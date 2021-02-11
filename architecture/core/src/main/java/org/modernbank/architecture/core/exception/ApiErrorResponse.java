package org.modernbank.architecture.core.exception;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiErrorResponse {
    @ApiModelProperty(value = "List of errors" , required = true)
	private List<ApiError> errors = new ArrayList<>();
}
