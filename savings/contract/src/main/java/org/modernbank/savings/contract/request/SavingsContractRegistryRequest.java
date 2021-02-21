package org.modernbank.savings.contract.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.modernbank.savings.contract.utilitiy.ContractStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Savings contract registration request")
@JsonInclude(Include.NON_NULL)
public class SavingsContractRegistryRequest {
 
    @ApiModelProperty(value =
            "Contract status\n" +
            "- IDLE: pending to be activated\n" +
            "- ACTIVE: contract available for operationsd\n" +
            "- BLOCKED: no operations on contract allowed\n" +
            "- CLOSED: contract is not longer available; historical data only\n",
            example = "ACTIVE",
            required = true)
    private ContractStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@ApiModelProperty(value = 
			"Contract sign-off date\n" + 
			"ISO 8601 Complete Data format (YYYY-MM-DD HH:MM:SS.sss):\n" + 
			"     YYYY = four-digit year\n" + 
			"     MM   = two-digit month (01=January, etc.)\n" + 
			"     DD   = two-digit day of month (01 through 31)\n" +
            "     hh   = two-digit hours (00 through 23)\n" + 
            "     mm   = two-digit minutes (00 through 59)\n" +
            "     ss   = two-digit seconds (00 through 59)\n" +
            "     SSS  = three-digit miliseconds (000 through 999)\n",
            example = "2020-01-18 21:36:45.123",
            required = false)	
	private Date signoffDate;

    @ApiModelProperty(value = "Participants list\n", required = true)
    private List<SavingsContractParticipantRegistryRequest> participantsList;
}
