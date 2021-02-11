package org.modernbank.savings.contract.assembler;

import org.springframework.stereotype.Component;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.modernbank.savings.contract.controller.SavingsContractController;
import org.modernbank.savings.contract.model.SavingsContractModel;

@Component
public class SavingsContractModelAssembler implements RepresentationModelAssembler<SavingsContractModel, EntityModel<SavingsContractModel>> {

    @Override
    public EntityModel<SavingsContractModel> toModel(SavingsContractModel entity) {

        return EntityModel.of(entity,
            linkTo(methodOn(SavingsContractController.class).getContract(entity.getUuid())).withSelfRel(),
            linkTo(methodOn(SavingsContractController.class).searchContracts(null, null)).withRel("savings"));
    }
    
}
