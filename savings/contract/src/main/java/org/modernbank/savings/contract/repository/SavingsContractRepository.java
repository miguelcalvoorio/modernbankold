package org.modernbank.savings.contract.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.modernbank.savings.contract.model.SavingsContractModel;

public interface SavingsContractRepository extends MongoRepository<SavingsContractModel, String>, SavingsContractRepositoryCustom {
    public SavingsContractModel findByUuid(String uuid);
}