package org.modernbank.savings.contract.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;

public class SavingsContractRepositoryImpl implements SavingsContractRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SavingsContractRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<SavingsContractModel> searchContracts(SavingsContractSearchRequest filter) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        
        if (filter.getStatus() != null) criteria.add(Criteria.where("status").is(filter.getStatus().toString()));
        if (filter.getIbanNumber() != null) criteria.add(Criteria.where("ibanNumber").is(filter.getIbanNumber()));
        if (filter.getParticipantUuid() != null) criteria.add(Criteria.where("participantsList.uuid").is(filter.getParticipantUuid()));

        if (!criteria.isEmpty()) query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        return mongoTemplate.find(query, SavingsContractModel.class);
    }

}