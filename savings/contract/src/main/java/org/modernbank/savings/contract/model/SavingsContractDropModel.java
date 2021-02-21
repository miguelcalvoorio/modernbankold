package org.modernbank.savings.contract.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "drop_contract")
public class SavingsContractDropModel extends SavingsContractModel {
   
    public SavingsContractDropModel(SavingsContractModel model) {
        super();
        this.createFromContractModel(model);
    }
    
    public void createFromContractModel(SavingsContractModel model) {
        this.set_id(model.get_id());
        this.setUuid(model.getUuid());
        this.setIbanNumber(model.getIbanNumber());
        this.setStatus(model.getStatus());
        this.setCreationDate(model.getCreationDate());
        this.setSignoffDate(model.getSignoffDate());
        this.setParticipantsList(model.getParticipantsList());
        this.setLastModificationDate(model.getLastModificationDate());
        this.setLastModificationBy(model.getLastModificationBy());
    }
}
