package org.modernbank.architecture.core.exception;

import java.util.List;

public class ResourceNotFoundException extends ApiException {
        
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public ResourceNotFoundException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorCode, errorMessage, errorParams);
    }
}
