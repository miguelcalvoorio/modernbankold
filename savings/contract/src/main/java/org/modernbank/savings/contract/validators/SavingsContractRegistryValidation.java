package org.modernbank.savings.contract.validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.modernbank.architecture.core.exception.InputDataException;
import org.modernbank.architecture.core.security.RequesterData;
import org.modernbank.architecture.core.security.HeaderValidations;
import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.model.SavingsContractParticipantModel;
import org.modernbank.savings.contract.request.SavingsContractParticipantRegistryRequest;
import org.modernbank.savings.contract.request.SavingsContractRegistryRequest;
import org.modernbank.savings.contract.utilitiy.ContractStatus;

public class SavingsContractRegistryValidation {
    
    private SavingsContractRegistryValidation() {}

    public static SavingsContractModel doValidations(SavingsContractRegistryRequest newContractRequest) throws InputDataException {
        final RequesterData requesterData = HeaderValidations.getRequesterData();
        SavingsContractModel newContract = new SavingsContractModel();

        Date today = new Date();

        try {
            newContract.setUuid(getNewUuid());
            newContract.setIbanNumber(getNewIBAN());
            if (newContractRequest.getSignoffDate() == null) {
                // Contract open as IDLE
                newContract.setStatus(ContractStatus.IDLE);
            } else {
                newContract.setSignoffDate(newContractRequest.getSignoffDate());
                newContract.setStatus(newContractRequest.getStatus());
            }
            newContract.setCreationDate(today);
            newContract.setLastModificationDate(today);
            newContract.setLastModificationBy(requesterData.getUuid());

            List<SavingsContractParticipantModel> participantsList = new ArrayList<>();
            for (SavingsContractParticipantRegistryRequest participantRequest : newContractRequest.getParticipantsList()) {
                SavingsContractParticipantModel participantModel = new SavingsContractParticipantModel();
                participantModel.setUuid(participantRequest.getUuid());
                participantModel.setParticipationType(participantRequest.getParticipationType());
                participantModel.setLastModificationDate(today);
                participantModel.setLastModificationBy(requesterData.getUuid());

                participantsList.add(participantModel);
            }
            newContract.setParticipantsList(participantsList);

        } catch (Exception ex) {
            // Error E1001: Input data validation
            String errorCode = "E1001";
            String errorMessage = "Input data error";
            List<String> errorParams = new ArrayList<>();
            throw new InputDataException(errorCode, errorMessage, errorParams);
        }

        return newContract;
    }

    private static String getNewIBAN() {
        // We should replace this method for a real IBAN number generator
        String iban = "ES91";
        String entity = String.format("%04d", getRandomNumberInRange(4));
        String branch = String.format("%04d", getRandomNumberInRange(4));
        String control = String.format("%02d", getRandomNumberInRange(2));
        String contract = String.format("%010d", getRandomNumberInRange(10));

        return iban + " " + entity + " " + branch + " " + control + " " + contract;
    }

    private static String getNewUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private static long getRandomNumberInRange(int size) {
        if (size  > 10) {
            throw new IllegalArgumentException("maximun number of digits is 10");
        }
        return Math.abs(((new Random()).nextLong() % ((long)Math.pow(10, size))));
    }
}
