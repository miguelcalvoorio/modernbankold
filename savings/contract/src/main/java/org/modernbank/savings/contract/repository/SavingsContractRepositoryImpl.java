package org.modernbank.savings.contract.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.extern.slf4j.Slf4j;

import org.modernbank.savings.contract.model.SavingsContractModel;
import org.modernbank.savings.contract.request.SavingsContractSearchRequest;

@Slf4j
public class SavingsContractRepositoryImpl implements SavingsContractRepositoryCustom {
    private static final String PARTICIPANTS_LIST_ELEMENT = "participantsList.uuid";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SavingsContractRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<SavingsContractModel> searchContracts(String clientUuid, SavingsContractSearchRequest filter) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        if (filter.getStatus() != null) criteria.add(Criteria.where("status").is(filter.getStatus().toString()));
        if (filter.getIbanNumber() != null) criteria.add(Criteria.where("ibanNumber").is(filter.getIbanNumber()));
        if (clientUuid != null && filter.getParticipantUuid() != null) {
            criteria.add(
                Criteria.where(PARTICIPANTS_LIST_ELEMENT).is(clientUuid)
                    .andOperator(Criteria.where(PARTICIPANTS_LIST_ELEMENT).is(filter.getParticipantUuid())));
        } else {
            if (clientUuid != null) {
                criteria.add(Criteria.where(PARTICIPANTS_LIST_ELEMENT).is(clientUuid));
            }
            if (filter.getParticipantUuid() != null) {
                criteria.add(Criteria.where(PARTICIPANTS_LIST_ELEMENT).is(filter.getParticipantUuid()));
            }
        }

        if (!criteria.isEmpty()) query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        log.debug("Query --> " + query.getQueryObject().toJson());

        return mongoTemplate.find(query, SavingsContractModel.class);
    }


    @Override
    public List<SavingsContractModel> getAllContracts(String clientUuid) {        
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        
        if (clientUuid != null) criteria.add(Criteria.where(PARTICIPANTS_LIST_ELEMENT).is(clientUuid));
        
        if (!criteria.isEmpty()) query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        return mongoTemplate.find(query, SavingsContractModel.class);
    }
}