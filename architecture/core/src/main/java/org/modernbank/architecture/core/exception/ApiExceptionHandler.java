package org.modernbank.architecture.core.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        ApiError error = new ApiError(); 
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        List<String> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        
        for (String errorMessage : errorList) {
            error = new ApiError();  
            error.setErrorCode("N/A");
            error.setErrorLevel("ERROR");
            error.setErrorMessage("Contact with your administrator");
            error.setErrorDescription(errorMessage);
        }
        
		apiErrorResponse.getErrors().add(error);

		return apiErrorResponse;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handlerNotFoundException(ResourceNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.getErrors().add(createErrorResponse(ex));
        
        return apiErrorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handlerException(Exception ex) {
        ApiError error = new ApiError();
        error.setErrorCode("N/A");
		error.setErrorLevel("ERROR");
        error.setErrorMessage("Contact with your administrator");
        error.setErrorDescription(ex.getMessage());

		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.getErrors().add(error);

		return apiErrorResponse;
    }

    private ApiError createErrorResponse(ApiException ex) {
        String prefixLabel = "error.";

        ApiError error = new ApiError();

        error.setErrorCode(ex.getErrorCode());
        try {
            String errorLevel = ErrorMessages.getMessageForLocale(prefixLabel + ex.getErrorCode() + ".level");
            String errorMessage = ErrorMessages.getMessageForLocale(prefixLabel + ex.getErrorCode() + ".message");
            String errorDescription = ErrorMessages.getMessageForLocale(prefixLabel + ex.getErrorCode() + ".description");
            
            for (int i = 0; i < ex.getErrorParams().size(); i++) {
                errorMessage = errorMessage.replace("{" + (i+1) + "}", ex.getErrorParams().get(i));
                errorDescription = errorDescription.replace("{" + (i+1) + "}", ex.getErrorParams().get(i));
            }

            error.setErrorLevel(errorLevel);
            error.setErrorMessage(errorMessage);
            error.setErrorDescription(errorDescription);
        } catch (Exception exception) {
            error.setErrorLevel("ERROR");
            error.setErrorMessage("Contact with your administrator");
            error.setErrorDescription(ex.getMessage());
        }

        return error;
    }
}
