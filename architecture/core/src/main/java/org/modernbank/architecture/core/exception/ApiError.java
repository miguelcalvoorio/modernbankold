package org.modernbank.architecture.core.exception;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel
public class ApiError {

    @ApiModelProperty(value = "Unique alphanumeric human readable error code", example = "E1001", required = true)
    private String errorCode;

    @ApiModelProperty(value = "Severity level; possible values: INFO, WARNING, ERROR", example = "ERROR", required = true)
    private String errorLevel;
    
    @ApiModelProperty(value = "Brief error summary", example="Error message", required = true)
    private String errorMessage;

    @ApiModelProperty(value = "Detailed description of the error", example = "Error description", required = true)
    private String errorDescription;

}
