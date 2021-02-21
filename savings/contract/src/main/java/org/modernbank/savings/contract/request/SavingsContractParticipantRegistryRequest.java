package org.modernbank.savings.contract.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.modernbank.savings.contract.utilitiy.ParticipationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Savings participant registration request")
@JsonInclude(Include.NON_NULL)
public class SavingsContractParticipantRegistryRequest {

    @ApiModelProperty(value = "Participant universally unique identifier", example = "123e4567-e89b-42d3-a456-556642440000", required = true)
    private String uuid;

    @ApiModelProperty(value = 
            "Participation type\n" +
            "- OWNER: contract administration role\n" + 
            "- ALLOWED: contract operation role\n" +
            "- INFORMED: contract read-only role\n",
            example ="OWNER",
            required = true)
    private ParticipationType participationType;
}
