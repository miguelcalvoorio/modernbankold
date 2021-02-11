package org.modernbank.architecture.core.exception;

import java.util.Collections;
import java.util.List;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final List<String> errorParams;

    public ApiException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorParams = errorParams;
    }

    public ApiException(String errorMessage) {
        this("E0001", errorMessage, Collections.<String>emptyList());
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public List<String> getErrorParams() {
        return this.errorParams;
    }
}
