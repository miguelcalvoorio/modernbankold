package org.modernbank.savings.contract.assembler;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.modernbank.savings.contract.controller.SavingsContractController;
import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.response.SavingsContractResponse;
import org.modernbank.savings.contract.response.SavingsContractParticipantResponse;

@Component
public class SavingsContractModelAssembler implements RepresentationModelAssembler<SavingsContractModel, SavingsContractResponse> {

    @Autowired
    private SavingsContracParticipantModelAssembler participantAssembler;
    
    @Override
    public SavingsContractResponse toModel(SavingsContractModel entity) {

        SavingsContractResponse contractResponse = new SavingsContractResponse();
        contractResponse.setUuid(entity.getUuid());
        contractResponse.setIbanNumber(entity.getIbanNumber());
        contractResponse.setStatus(entity.getStatus());
        contractResponse.setCreationDate(entity.getCreationDate());
        contractResponse.setSignoffDate(entity.getSignoffDate());
        contractResponse.setLastModificationDate(entity.getLastModificationDate());
        contractResponse.setLastModificationBy(entity.getLastModificationBy());

        if (entity.getParticipantsList() != null) {
            List<SavingsContractParticipantResponse> participantsList = entity.getParticipantsList().stream()
                .map(participantAssembler::toModel)
                .collect(Collectors.toList());

            for (SavingsContractParticipantResponse participantResponse : participantsList) {
                participantResponse.add(linkTo(methodOn(SavingsContractController.class).getContract(entity.getUuid())).withSelfRel());
            }
            
            contractResponse.setParticipantsList(participantsList);
        }
        
        contractResponse.add(
            linkTo(methodOn(SavingsContractController.class).getContract(entity.getUuid())).withSelfRel(),
            linkTo(methodOn(SavingsContractController.class).searchContracts(null, null)).withRel("savings"));

        return contractResponse;      
    }
    
}
