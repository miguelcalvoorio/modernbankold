package org.modernbank.savings.contract.assembler;

import org.springframework.stereotype.Component;

import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.modernbank.savings.contract.model.SavingsContractParticipantModel;
import org.modernbank.savings.contract.response.SavingsContractParticipantResponse;

@Component
public class SavingsContracParticipantModelAssembler implements RepresentationModelAssembler<SavingsContractParticipantModel, SavingsContractParticipantResponse> {

    @Override
    public SavingsContractParticipantResponse toModel(SavingsContractParticipantModel entity) {
        SavingsContractParticipantResponse participantResponse = new SavingsContractParticipantResponse();

        participantResponse.setUuid(entity.getUuid());
        participantResponse.setName(entity.getName());
        participantResponse.setParticipationType(entity.getParticipationType());
        participantResponse.setLastModificationDate(entity.getLastModificationDate());
        participantResponse.setLastModificationBy(entity.getLastModificationBy());

        return participantResponse;      
    }
    
}
