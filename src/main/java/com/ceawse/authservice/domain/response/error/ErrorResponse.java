package com.ceawse.authservice.domain.response.error;

import com.ceawse.authservice.domain.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;

@lombok.Data
@Builder
@AllArgsConstructor
public class ErrorResponse implements Response {

    private Data data;
    private boolean informative;
    private StackTraceElement[] stacktrace;
    private boolean success;
    private int status;
}
