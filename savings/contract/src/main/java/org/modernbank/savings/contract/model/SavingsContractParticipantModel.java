package org.modernbank.savings.contract.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
@JsonInclude(Include.NON_NULL)
public class SavingsContractParticipantModel {
    @ApiModel
    private enum ParticipationType {OWNER, ALLOWED, INFORMED}

    @ApiModelProperty(value = "Participant universally unique identifier", example = "123e4567-e89b-42d3-a456-556642440000")
    private String uuid;

    @ApiModelProperty(value = 
            "Participation type\n" +
            "- OWNER: contract administration role\n" + 
            "- ALLOWED: contract operation role\n" +
            "- INFORMED: contract read-only role\n",
            example ="OWNER")
    private ParticipationType participationType;

    @ApiModelProperty(value = "Participant's full name", example = "Doe, John")
    private String name;

    @ApiModelProperty(value = "Last modification person", example = "123e4567-e89b-12d3-a456-426614174000")
    private String lastPersonModification;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@ApiModelProperty(value = 
			"Last modification date\n" + 
			"ISO 8601 Complete Data format (YYYY-MM-DD HH:MM:SS.sss):\n" + 
			"     YYYY = four-digit year\n" + 
			"     MM   = two-digit month (01=January, etc.)\n" + 
			"     DD   = two-digit day of month (01 through 31)\n" +
            "     hh   = two-digit hours (00 through 23)\n" + 
            "     mm   = two-digit minutes (00 through 59)\n" +
            "     ss   = two-digit seconds (00 through 59)\n" +
            "     SSS  = three-digit miliseconds (000 through 999)\n",
            example = "2020-01-18 21:36:45.123")	
	private Date lastModificationDate;
}
