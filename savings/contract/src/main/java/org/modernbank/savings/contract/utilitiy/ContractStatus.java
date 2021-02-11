package org.modernbank.savings.contract.utilitiy;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Contract status\n" +
                  "- IDLE: pending to be activated\n" +
                  "- ACTIVE: contract available for operationsd\n" +
                  "- BLOCKED: no operations on contract allowed\n" +
                  "- CLOSED: contract is not longer available; historical data only\n")
public enum ContractStatus {
    IDLE,
    ACTIVE,
    BLOCKED,
    CLOSED;
}
