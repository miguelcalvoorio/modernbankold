package org.modernbank.savings.contract.controller.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.modernbank.architecture.core.exception.ApiErrorResponse;

import org.springframework.http.ResponseEntity;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiOperation(
    value = "This operations allows searching for savings contracts\r\n",
    notes = "This operations allows searching for savings contracts using the following filtering criteria:\r\n"
          + "- Participant (Person uId)\r\n"
          + "- Status (PENDING TO BE ACTIVATED, ACTIVE, BLOCKED, CLOSED)\r\n"
          + "- Sign-off date\r\n",
    tags  = { "Savings contracts" },
    response = ResponseEntity.class
)
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "Bad request", response = ApiErrorResponse.class),
    @ApiResponse(code = 401, message = "Unauthorized", response = ApiErrorResponse.class),
    @ApiResponse(code = 403, message = "Forbidden", response = ApiErrorResponse.class),
    @ApiResponse(code = 404, message = "Not Found", response = ApiErrorResponse.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorResponse.class),
    @ApiResponse(code = 503, message = "Service Unavailable", response = ApiErrorResponse.class),
    @ApiResponse(code = 504, message = "Gateway Timeout", response = ApiErrorResponse.class)
})
public @interface SavingsContractListDocument {}
