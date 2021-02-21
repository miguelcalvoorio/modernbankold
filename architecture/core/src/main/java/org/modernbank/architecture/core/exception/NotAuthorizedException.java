package org.modernbank.architecture.core.exception;

import java.util.List;

public class NotAuthorizedException extends ApiException {
    private static final long serialVersionUID = 1L;

    public NotAuthorizedException(String errorMessage) {
        super(errorMessage);
    }

    public NotAuthorizedException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorCode, errorMessage, errorParams);
    }
    
}
