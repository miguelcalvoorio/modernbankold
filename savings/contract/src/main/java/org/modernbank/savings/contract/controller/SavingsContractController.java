package org.modernbank.savings.contract.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modernbank.savings.contract.assembler.SavingsContractModelAssembler;
import org.modernbank.savings.contract.controller.document.SavingsContractDeleteDocument;
import org.modernbank.savings.contract.controller.document.SavingsContractDetailDocument;
import org.modernbank.savings.contract.controller.document.SavingsContractListDocument;
import org.modernbank.savings.contract.controller.document.SavingsContractRegistryDocument;
import org.modernbank.savings.contract.controller.document.SavingsContractSearchDocument;
import org.modernbank.savings.contract.controller.document.SavingsContractUpdateDocument;
import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.request.SavingsContractRegistryRequest;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;
import org.modernbank.savings.contract.request.SavingsContractUpdateRequest;
import org.modernbank.savings.contract.response.SavingsContractDeleteResponse;
import org.modernbank.savings.contract.response.SavingsContractListResponse;
import org.modernbank.savings.contract.response.SavingsContractResponse;
import org.modernbank.savings.contract.service.SavingsContractService;
import org.modernbank.savings.contract.validators.SavingsContractRegistryValidation;

@RestController
@Api(tags = "Savings contracts")
public class SavingsContractController {
    private final SavingsContractModelAssembler assembler;
    private final SavingsContractService service;

    SavingsContractController(final SavingsContractModelAssembler assembler, final SavingsContractService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractSearchDocument
    @PostMapping(value = "/savings/search", produces = { "application/hal+json" })
    public SavingsContractListResponse searchContracts(
        @RequestBody SavingsContractSearchRequest filter,
		HttpServletRequest httpServletRequest) {
            
        List<SavingsContractResponse> contractList = service.searchContracts(filter).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        SavingsContractListResponse response = new SavingsContractListResponse();
        response.setContractList(contractList);

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractListDocument
    @GetMapping(value = "/savings/", produces = { "application/hal+json" })
    public SavingsContractListResponse getContractsList() {

        List<SavingsContractResponse> contractList = service.getAllContracts().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        SavingsContractListResponse response = new SavingsContractListResponse();
        response.setContractList(contractList);

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractDetailDocument
    @GetMapping(value = "/savings/{contractUuid}", produces = { "application/hal+json" })
    public SavingsContractResponse getContract(
        @ApiParam(value = "Contract unique ID", required = true, example = "123e4567-e89b-42d3-a456-556642440000")
        //@Size(min = 36, max = 36, message = "unique ID structure: XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX")
        @PathVariable("contractUuid") String contractUuid) {
    
            return assembler.toModel(service.getContract(contractUuid));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @SavingsContractRegistryDocument
    @PostMapping(value = "/savings/", produces = { "application/hal+json" })
    public SavingsContractResponse newContract(@RequestBody SavingsContractRegistryRequest contractRequest) {
        return assembler.toModel(service.recordNewContract(SavingsContractRegistryValidation.doValidations(contractRequest)));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractUpdateDocument
    @PatchMapping(value = "/savings/{contractUuid}", produces = { "application/hal+json" })
    public SavingsContractResponse updateContract(
        @ApiParam(value = "Contract unique ID", required = true, example = "123e4567-e89b-42d3-a456-556642440000")
        @PathVariable("contractUuid") String contractUuid,
        
        @RequestBody SavingsContractUpdateRequest dataToUpdate,
		HttpServletRequest httpServletRequest) {

        return assembler.toModel(service.updateContract(contractUuid, dataToUpdate));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @SavingsContractDeleteDocument
    @DeleteMapping(value = "/savings/{contractUuid}", produces = { "application/hal+json" })
    public SavingsContractDeleteResponse deleteContract(
        @ApiParam(value = "Contract unique ID", required = true, example = "123e4567-e89b-42d3-a456-556642440000")
        @PathVariable("contractUuid") String contractUuid) {
        SavingsContractModel contract = service.deleteContract(contractUuid);

        SavingsContractDeleteResponse response = new SavingsContractDeleteResponse();
        response.setUuid(contract.getUuid());
        response.setIbanNumber(contract.getIbanNumber());
        response.setStatus(contract.getStatus());
        response.setDeletedBy(contract.getLastModificationBy());
        response.setDeletedDate(contract.getLastModificationDate());

        return response;
    }
}
