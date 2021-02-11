package org.modernbank.savings.contract.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.modernbank.savings.contract.utilitiy.ContractStatus;

@Data
@Document(collection = "contract")
@Relation(itemRelation = "contract", collectionRelation = "contractList")
@ApiModel
@JsonInclude(Include.NON_NULL)
public class SavingsContractModel {

    @ApiModelProperty(value = "Contract universally unique identifier\n", example = "123e4567-e89b-42d3-a456-556642440000")
    private String uuid;

    @ApiModelProperty(value = "IBAN contract number\n", example ="ES91 2100 0418 4502 0005 1332")
    private String ibanNumber;

    @ApiModelProperty(value =
            "Contract status\n" +
            "- IDLE: pending to be activated\n" +
            "- ACTIVE: contract available for operationsd\n" +
            "- BLOCKED: no operations on contract allowed\n" +
            "- CLOSED: contract is not longer available; historical data only\n",
            example = "ACTIVE")
    private ContractStatus status;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@ApiModelProperty(value = 
			"Contract creation date\n" + 
			"ISO 8601 Complete Data format (YYYY-MM-DD HH:MM:SS.sss):\n" + 
			"     YYYY = four-digit year\n" + 
			"     MM   = two-digit month (01=January, etc.)\n" + 
			"     DD   = two-digit day of month (01 through 31)\n" +
            "     hh   = two-digit hours (00 through 23)\n" + 
            "     mm   = two-digit minutes (00 through 59)\n" +
            "     ss   = two-digit seconds (00 through 59)\n" +
            "     SSS  = three-digit miliseconds (000 through 999)\n",
            example = "2020-01-18 21:36:45.123")	
	private Date creationDate;

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
            example = "2020-01-18 21:36:45.123")	
	private Date signoffDate;

    @ApiModelProperty(value = "Participants list\n")
    private List<SavingsContractParticipantModel> participantsList;

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
