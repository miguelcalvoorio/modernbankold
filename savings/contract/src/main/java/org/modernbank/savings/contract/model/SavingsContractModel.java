package org.modernbank.savings.contract.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.modernbank.savings.contract.utilitiy.ContractStatus;

@Data
@Document(collection = "contract")
public class SavingsContractModel {

    private String _id;
    private String uuid;
    private String ibanNumber;
    private ContractStatus status;	
	private Date creationDate;
	private Date signoffDate;
    private List<SavingsContractParticipantModel> participantsList;
    private String lastModificationBy;	
	private Date lastModificationDate;
}
