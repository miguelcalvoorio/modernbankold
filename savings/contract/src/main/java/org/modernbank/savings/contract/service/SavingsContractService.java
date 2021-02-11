package org.modernbank.savings.contract.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.modernbank.architecture.core.exception.ResourceNotFoundException;
import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.repository.SavingsContractRepository;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;

@Service
public class SavingsContractService {
    
    private final SavingsContractRepository repository;

    public SavingsContractService(final SavingsContractRepository repository) {
        this.repository = repository;
    }

    public SavingsContractModel getContract(String contractUuid) throws ResourceNotFoundException {
        SavingsContractModel contract = this.repository.findByUuid(contractUuid);

        if (contract == null) {
            // Error E2001: contract not found
            String errorCode = "E2001";
            String errorMessage = "Contact not " + contractUuid + " found";
            List<String> errorParams = new ArrayList<>();
            errorParams.add(contractUuid);

            throw new ResourceNotFoundException(errorCode, errorMessage, errorParams);
        }
        return contract;
    }

    public List<SavingsContractModel> searchContracts(SavingsContractSearchRequest filter) {
        List<SavingsContractModel> contractList = this.repository.searchContracts(filter);

        if (contractList == null || contractList.isEmpty()) {            
            // Error E2002: no records found for search criteria
            String errorCode = "E2002";
            String errorMessage = "No records found for search criteria";
            List<String> errorParams = new ArrayList<>();

            throw new ResourceNotFoundException(errorCode, errorMessage, errorParams);
        } 
        return contractList;
    }
}
