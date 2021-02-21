package org.modernbank.architecture.core.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedFailureHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Error E0002: Specific error
        String errorCode = "E0002";
        String errorMessage = "Not authorized to access " + request.getRequestURI();
        List<String> errorParams = new ArrayList<>();
        errorParams.add(request.getRequestURI());

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(
                ExceptionUtils.createErrorResponse(new NotAuthorizedException(errorCode, errorMessage, errorParams)));

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(apiErrorResponse));
        response.getWriter().flush();
    }
}