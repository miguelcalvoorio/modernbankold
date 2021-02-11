package org.modernbank.savings.contract.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.modernbank.savings.contract.utilitiy.ContractStatus;

@Data
@ApiModel
@JsonInclude(Include.NON_NULL)
public class SavingsContractSearchRequest {

    @ApiModelProperty(required = false, value = "Contract universally unique identifier\n", example = "123e4567-e89b-42d3-a456-556642440000")
    private String participantUuid;

    @ApiModelProperty(required = false, value = "IBAN contract number\n", example ="ES91 2100 0418 4502 0005 1332")
    private String ibanNumber;

    @ApiModelProperty(required = false, value =
            "Contract status\n" +
            "- IDLE: pending to be activated\n" +
            "- ACTIVE: contract available for operationsd\n" +
            "- BLOCKED: no operations on contract allowed\n" +
            "- CLOSED: contract is not longer available; historical data only\n",
            example = "ACTIVE")
    private ContractStatus status;
}
