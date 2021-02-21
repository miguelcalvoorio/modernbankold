package org.modernbank.architecture.core.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtils {

    private ExceptionUtils() {}

    public static final String ERROR_GENERIC_CODE = "N/A";
    public static final String ERROR_GENERIC_MESSAGE = "Contact with your administrator";

    public enum ErrorLevel {
        INFO, WARNING, ERROR
    }

    public static ApiError createErrorResponse(ApiException ex) {
        String prefixLabel = "error.";

        ApiError error = new ApiError();

        error.setErrorCode(ex.getErrorCode());
        try {
            String errorLevel = ErrorMessages.getMessageForLocale(prefixLabel + ex.getErrorCode() + ".level");
            String errorMessage = ErrorMessages.getMessageForLocale(prefixLabel + ex.getErrorCode() + ".message");
            String errorDescription = ErrorMessages
                    .getMessageForLocale(prefixLabel + ex.getErrorCode() + ".description");

            for (int i = 0; i < ex.getErrorParams().size(); i++) {
                errorMessage = errorMessage.replace("{" + (i + 1) + "}", ex.getErrorParams().get(i));
                errorDescription = errorDescription.replace("{" + (i + 1) + "}", ex.getErrorParams().get(i));
            }

            error.setErrorLevel(errorLevel);
            error.setErrorMessage(errorMessage);
            error.setErrorDescription(errorDescription);
        } catch (Exception exception) {
            error.setErrorLevel(ErrorLevel.ERROR.toString());
            error.setErrorMessage(ERROR_GENERIC_MESSAGE);
            error.setErrorDescription(ex.getMessage());
        }

        log.error("[" + error.getErrorCode() + "] Internal message -> " + ex.getMessage());

        return error;
    }
}
