package org.modernbank.savings.contract.model;

import java.util.Date;

import org.modernbank.savings.contract.utilitiy.ParticipationType;

import lombok.Data;

@Data
public class SavingsContractParticipantModel {
    private String uuid;
    private ParticipationType participationType;
    private String name;
    private String lastModificationBy;
	private Date lastModificationDate;
}
