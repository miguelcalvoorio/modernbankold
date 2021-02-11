package org.modernbank.architecture.core.exception;

import java.util.List;

public class BadRequestException extends ApiException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

    public BadRequestException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorCode, errorMessage, errorParams);
    }
}
