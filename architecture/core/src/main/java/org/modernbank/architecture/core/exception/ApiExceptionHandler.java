package org.modernbank.architecture.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InputDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handlerInputDataException(InputDataException ex) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        for (ApiException apiexception : ex.getExceptionsList())
            apiErrorResponse.getErrors().add(ExceptionUtils.createErrorResponse(apiexception));
        return apiErrorResponse;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handlerNotFoundException(ResourceNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(ExceptionUtils.createErrorResponse(ex));

        return apiErrorResponse;
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handlerNotAuthorizedException(NotAuthorizedException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(ExceptionUtils.createErrorResponse(ex));

        return apiErrorResponse;
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handlerForbiddenException(ForbiddenException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(ExceptionUtils.createErrorResponse(ex));

        return apiErrorResponse;
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handlerInternalServerException(InternalServerException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(ExceptionUtils.createErrorResponse(ex));

        return apiErrorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handlerException(Exception ex) {
        log.error("Internal server error; exception {}", ex);

        ApiError error = new ApiError();
        error.setErrorCode(ExceptionUtils.ERROR_GENERIC_CODE);
        error.setErrorLevel(ExceptionUtils.ErrorLevel.ERROR.toString());
        error.setErrorMessage(ExceptionUtils.ERROR_GENERIC_MESSAGE);
        error.setErrorDescription(ex.getMessage());

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(error);

        return apiErrorResponse;
    }
}
