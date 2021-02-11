package org.modernbank.savings.contract.repository;

import java.util.List;

import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;

public interface SavingsContractRepositoryCustom {
    List<SavingsContractModel> searchContracts(SavingsContractSearchRequest filter);
}
