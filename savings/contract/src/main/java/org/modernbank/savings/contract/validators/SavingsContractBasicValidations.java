package org.modernbank.savings.contract.validators;

import org.springframework.stereotype.Service;

@Service
public class SavingsContractBasicValidations {

    public boolean isValidDate(String date) {
        return true;
    }

    public boolean isValidUuid(String uuid) {
        return true;
    }
}
