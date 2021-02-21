package org.modernbank.architecture.core.exception;

import java.util.ArrayList;
import java.util.List;

public class InputDataException extends ApiException {

    private static final long serialVersionUID = 1L;
    private List<ApiException> exceptionsList = new ArrayList<>();

    public InputDataException(String errorMessage) {
        super(errorMessage);
    }

    public InputDataException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorCode, errorMessage, errorParams);
    }

    public void addException(ApiException exception) {
        this.exceptionsList.add(exception);
    }

    public List<ApiException> getExceptionsList() {
        return this.exceptionsList;
    }
}
