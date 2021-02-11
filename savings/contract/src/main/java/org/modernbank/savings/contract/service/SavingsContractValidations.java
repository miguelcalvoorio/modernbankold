package org.modernbank.savings.contract.service;

import org.springframework.stereotype.Service;

@Service
public class SavingsContractValidations {

    public boolean isValidDate(String date) {
        return true;
    }

    public boolean isValidUuid(String uuid) {
        return true;
    }
}
