package com.ceawse.authservice.service.impl;

import com.ceawse.authservice.component.OTPStore;
import com.ceawse.authservice.component.RegistrationStore;
import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.ceawse.authservice.domain.constant.Code;
import com.ceawse.authservice.domain.response.exception.information.EmailSendingException;
import com.ceawse.authservice.domain.response.exception.information.InformationException;
import com.ceawse.authservice.domain.response.exception.information.RegistrationException;
import com.ceawse.authservice.repository.UserRepository;
import com.ceawse.authservice.service.RegistrationService;
import com.ceawse.authservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    @Value("${EMAIL_TEMPLATE}")
    private String TEMPLATE_PATH;
    private final RegistrationStore registrationStore;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OTPStore otpStore;
    private final Session mailSession;

    @Override
    public void register(RegistrationReq req, HttpServletResponse response) {
        if (userRepository.existsByEmail(req.getEmail())){
            throw InformationException.builder("error.account.already.exist").build();
        }

        OTPStore.GenerationResult generationResult = otpStore.generate(response);

        try {
            registrationStore.save(req, generationResult.sessionId());
        } catch (JsonProcessingException e) {
            throw InformationException.builder("error.json.processing").build();
        }

        sendEmail(req.getEmail(),
                generationResult.opt(),
                "Email verification code");
    }

    @Override
    public void checkOtp(String otp, HttpServletRequest request) {
        if (!otpStore.validate(otp, request)){
            throw new RegistrationException("otp.incorrect");
        }

        String sessionId = otpStore.getSessionId(request);
        RegistrationReq req;

        try {
            req = registrationStore.take(sessionId);
        } catch (JsonProcessingException e) {
            throw InformationException.builder(Code.NOT_READABLE,
                    "happened.unexpected.error", HttpStatus.BAD_REQUEST).build();
        }

        userService.saveAndActivate(req);
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            String htmlContent = getEmailTemplate().replace("{otp_code}", subject);

            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(to));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e);
            throw new EmailSendingException("email.sending.exception");
        }
    }

    private String getEmailTemplate() throws IOException {
        return Files.readString(Path.of(TEMPLATE_PATH));
    }
}
