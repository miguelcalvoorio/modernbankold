package org.modernbank.architecture.core.exception;

import java.util.List;

public class ForbiddenException extends ApiException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }

    public ForbiddenException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorCode, errorMessage, errorParams);
    }
}