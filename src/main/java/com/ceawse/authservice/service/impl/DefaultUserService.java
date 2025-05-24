package com.ceawse.authservice.service.impl;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.ceawse.authservice.domain.entity.UserEntity;
import com.ceawse.authservice.domain.response.exception.information.EmailSendingException;
import com.ceawse.authservice.repository.RoleRepository;
import com.ceawse.authservice.repository.UserRepository;
import com.ceawse.authservice.service.UserService;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    @Value("${EMAIL_TEMPLATE}")
    private String TEMPLATE_PATH;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Session mailSession;

    @Override
    public UserEntity saveAndActivate(RegistrationReq req) {
        UserEntity user = new UserEntity();
        user.setNickname(req.getNickname());
        user.setEmail(req.getEmail());
        user.getRoles().add(roleRepository.getDefaultRole());
        user.setActive(true);
        user.setPasswordHash(req.getPassword());
        user.setAdmin(false);
        user.setSuperuser(false);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
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
