package org.modernbank.savings.contract.utilitiy;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Participation type\n" +
                  "- OWNER: contract administration role\n" + 
                  "- ALLOWED: contract operation role\n" +
                  "- INFORMED: contract read-only role\n")
public enum ParticipationType {
    OWNER,
    ALLOWED,
    INFORMED;
}