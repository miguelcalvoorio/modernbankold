package org.modernbank.architecture.core.exception;

import java.util.List;

public class InternalServerException extends ApiException {
    private static final long serialVersionUID = 1L;

    public InternalServerException(String errorMessage) {
        super(errorMessage);
    }

    public InternalServerException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorCode, errorMessage, errorParams);
    }
}