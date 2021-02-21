package org.modernbank.savings.contract.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.modernbank.architecture.core.exception.ResourceNotFoundException;
import org.modernbank.architecture.core.exception.ApiException;
import org.modernbank.architecture.core.exception.InputDataException;
import org.modernbank.architecture.core.exception.InternalServerException;
import org.modernbank.architecture.core.exception.ForbiddenException;
import org.modernbank.architecture.core.security.HeaderValidations;
import org.modernbank.architecture.core.security.RequesterData;
import org.modernbank.savings.contract.model.SavingsContractDropModel;
import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.model.SavingsContractParticipantModel;
import org.modernbank.savings.contract.repository.SavingsContractDropRepository;
import org.modernbank.savings.contract.repository.SavingsContractRepository;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;
import org.modernbank.savings.contract.request.SavingsContractUpdateRequest;
import org.modernbank.savings.contract.utilitiy.ContractStatus;
import org.modernbank.savings.contract.utilitiy.ParticipationType;

@Service
public class SavingsContractService {
    
    private final SavingsContractRepository repository;
    private final SavingsContractDropRepository dropRepository;

    public SavingsContractService(final SavingsContractRepository repository, final SavingsContractDropRepository dropRepository) {
        this.repository = repository;
        this.dropRepository = dropRepository;
    }

    public List<SavingsContractModel> searchContracts(SavingsContractSearchRequest filter) throws ResourceNotFoundException {
        final RequesterData requesterData = HeaderValidations.getRequesterData();
        final String clientUuid = requesterData.getClientUuid();
        
        List<SavingsContractModel> contractList = this.repository.searchContracts(clientUuid, filter);

        if (contractList == null || contractList.isEmpty()) {            
            // Error E2002: no records found for search criteria
            String errorCode = "E2002";
            String errorMessage = "No records found for search criteria";
            List<String> errorParams = new ArrayList<>();

            throw new ResourceNotFoundException(errorCode, errorMessage, errorParams);
        } 
        return contractList;
    }

    public List<SavingsContractModel> getAllContracts() throws ResourceNotFoundException {
        final RequesterData requesterData = HeaderValidations.getRequesterData();
        final String clientUuid = requesterData.getClientUuid();

        List<SavingsContractModel> contractList = this.repository.getAllContracts(clientUuid);

        if (contractList.isEmpty()) {
            // Error E2003: no records found
            String errorCode = "E2003";
            String errorMessage = "No records found";
            List<String> errorParams = new ArrayList<>();
            
            throw new ResourceNotFoundException(errorCode, errorMessage, errorParams);
        } 
        return contractList;
    }

    public SavingsContractModel getContract(String contractUuid) throws ResourceNotFoundException {
        final RequesterData requesterData = HeaderValidations.getRequesterData();
        final String clientUuid = requesterData.getClientUuid();

        SavingsContractModel contract = this.repository.findByUuid(contractUuid);

        if (clientUuid != null) {
            // Check if client is part of contract's participants
            for (SavingsContractParticipantModel participant : contract.getParticipantsList()) {
                if (participant.getUuid().equals(clientUuid)) return contract;
            }
        }

        if (contract == null) {
            // Error E2001: contract not found
            String errorCode = "E2001";
            String errorMessage = "Contact " + contractUuid + " not found";
            List<String> errorParams = new ArrayList<>();
            errorParams.add(contractUuid);

            throw new ResourceNotFoundException(errorCode, errorMessage, errorParams);
        }
        return contract;
    }

    public SavingsContractModel recordNewContract(SavingsContractModel newContract) throws InternalServerException {        
        try {

            return this.repository.insert(newContract);
        } catch (Exception ex) {
            // Error E0001: error accesing database
            String errorCode = "E0001";
            String errorMessage = "Error accessing database";
            List<String> errorParams = new ArrayList<>();

            throw new InternalServerException(errorCode, errorMessage, errorParams);
        }
    }

    public SavingsContractModel updateContract(String contractUuid, SavingsContractUpdateRequest data)
        throws ResourceNotFoundException, InputDataException, InternalServerException {

        final RequesterData requesterData = HeaderValidations.getRequesterData();
        
        // 1. Retrieve contract saved
        SavingsContractModel contract = this.getContract(contractUuid);

        // 2. Check if user is allowed to update the contract
        checkUserCanWriteContract(contract);

        // 3. Update contract data
        Date today = new Date();

        if (data.getSignoffDate() != null) contract.setSignoffDate(data.getSignoffDate());
        if (data.getStatus() != null && contract.getSignoffDate() != null) {
            contract.setStatus(data.getStatus());
        } else {
            // Error E1001: status cannot be updated if contract has not been signed-off
            String errorCode = "E1001";
            String errorMessage = "Status cannot be updated if contract has not been signed-off";
            List<String> errorParams = new ArrayList<>();
            
            InputDataException exception = new InputDataException(errorCode, errorMessage, errorParams);

            // Error E1002: Specific error
            errorCode = "E1002";
            errorMessage = "Status cannot be updated if contract has not been signed-off";
            errorParams = new ArrayList<>();            
            exception.addException(new ApiException(errorCode, errorMessage, errorParams));

            throw exception;
        }
        contract.setLastModificationDate(today);
        contract.setLastModificationBy(requesterData.getUuid()); // We use login Uuid, not impersonation

        // 4. Save contract
        try {
            return this.repository.save(contract);

        } catch (Exception ex) {
            // Error E0001: error accesing database
            String errorCode = "E0001";
            String errorMessage = "Error accessing database";
            List<String> errorParams = new ArrayList<>();

            throw new InternalServerException(errorCode, errorMessage, errorParams);
        }
    }

    public SavingsContractModel deleteContract(String contractUuid)
        throws ResourceNotFoundException, InputDataException, InternalServerException {

        final RequesterData requesterData = HeaderValidations.getRequesterData();
        Date today = new Date();

        // 1. Retrieve contract saved
        SavingsContractModel contract = this.getContract(contractUuid);

        // 2. Check if user is allowed to update the contract
        checkUserCanWriteContract(contract);

        // 3. Check if contract can be deleted
        if (contract.getStatus() != ContractStatus.CLOSED) {
            // Error E1002: no records found for search criteria
            String errorCode = "E1001";
            String errorMessage = "Contract cannot be deleted";
            List<String> errorParams = new ArrayList<>();
            
            InputDataException exception = new InputDataException(errorCode, errorMessage, errorParams);

            // Error E1003: Specific error
            errorCode = "E1003";
            errorMessage = "Contract cannot be deleted if status is not CLOSED";
            errorParams = new ArrayList<>();            
            exception.addException(new ApiException(errorCode, errorMessage, errorParams));

            throw exception;
        }
        contract.setLastModificationBy(requesterData.getUuid());  // We use login Uuid, not impersonation
        contract.setLastModificationDate(today);

        // 4. Save contract on drop collection
        SavingsContractDropModel dropContract = new SavingsContractDropModel(contract);
        this.dropRepository.save(dropContract);
        
        // 5. Delete original contract
        this.repository.deleteById(contract.get_id());

        return contract;
    }

    private ParticipationType checkContractAccess(SavingsContractModel contract, String clientUuid) {
        // Check if client is part of contract's participants
        Iterator<SavingsContractParticipantModel> participantsList = contract.getParticipantsList().listIterator();
        while (participantsList.hasNext()) {
            SavingsContractParticipantModel participant = participantsList.next();
            if (participant.getUuid().equals(clientUuid)) return participant.getParticipationType();
        }
        return null;
    }

    private void checkUserCanWriteContract(SavingsContractModel contract) throws ForbiddenException {
        final RequesterData requesterData = HeaderValidations.getRequesterData();
        final String clientUuid = requesterData.getClientUuid();  
        final RequesterData.Scope scope = requesterData.getScope();

        if (scope == RequesterData.Scope.EMPLOYEE) return;

        if (scope == RequesterData.Scope.CLIENT) {
            ParticipationType participationType = checkContractAccess(contract, clientUuid);
            if (participationType == ParticipationType.OWNER || participationType == ParticipationType.ALLOWED) return;
        }
        
        // Error E0002: not allowed
        String errorCode = "E0002";
        String errorMessage = "Not allowed to access contract " + contract.getUuid();
        List<String> errorParams = new ArrayList<>();
        errorParams.add(contract.getUuid());

        throw new ForbiddenException(errorCode, errorMessage, errorParams);
    }
}
