package org.modernbank.savings.contract.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.modernbank.savings.contract.assembler.SavingsContractModelAssembler;
import org.modernbank.savings.contract.controller.document.SavingsContractDetailDocument;
import org.modernbank.savings.contract.controller.document.SavingsContractListDocument;
import org.modernbank.savings.contract.repository.SavingsContractRepository;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;
import org.modernbank.savings.contract.service.SavingsContractService;
import org.modernbank.savings.contract.model.SavingsContractModel;

@RestController
@Api(tags = "Savings API")
public class SavingsContractController {
    private final SavingsContractRepository repository;
    private final SavingsContractModelAssembler assembler;
    private final SavingsContractService service;

    SavingsContractController(final SavingsContractRepository repository, final SavingsContractModelAssembler assembler, final SavingsContractService service) {
        this.repository = repository;
        this.assembler = assembler;
        this.service = service;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractListDocument
    @PostMapping(value = "/savings/search", produces = { "application/hal+json" })
    public CollectionModel<EntityModel<SavingsContractModel>> searchContracts(
        @RequestBody SavingsContractSearchRequest filter,
		HttpServletRequest httpServletRequest) {

        List<EntityModel<SavingsContractModel>> savingsContractList = service.searchContracts(filter).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(savingsContractList,
            linkTo(methodOn(SavingsContractController.class).searchContracts(null, null)).withSelfRel());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractDetailDocument
    @GetMapping(value = "/savings/{contractUuid}", produces = { "application/hal+json" })
    public ResponseEntity<EntityModel<SavingsContractModel>> getContract(
        @ApiParam(value = "Contract unique ID", required = true, example = "123e4567-e89b-42d3-a456-556642440000")
        //@Size(min = 36, max = 36, message = "unique ID structure: XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX")
        @PathVariable("contractUuid") String contractUuid) {

        EntityModel<SavingsContractModel> entityModel = assembler.toModel(service.getContract(contractUuid));
    
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PostMapping("/savings")
    public ResponseEntity<RepresentationModel> newContract() {
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/savings/{id}")
    public ResponseEntity<RepresentationModel> updateContract(@PathVariable("id") String uid) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/savings/{id}")
    public ResponseEntity<RepresentationModel> deleteContract(@PathVariable("id") String uid) {
        return ResponseEntity.ok().build();
    }
}
