package org.modernbank.savings.contract.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.modernbank.savings.contract.model.SavingsContractDropModel;

public interface SavingsContractDropRepository extends MongoRepository<SavingsContractDropModel, String> {}
