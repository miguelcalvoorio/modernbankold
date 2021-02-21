package org.modernbank.architecture.core.security;

import java.util.List;

import lombok.Data;

@Data
public class RequesterData {
    public enum Scope {USER, CLIENT, EMPLOYEE}

    private String uuid;
    private String userName;
    private String formalName;
    private String firstName;
    private String lastName;
    private List<String> rolesList;
    private Scope scope;

    private String clientUuid;
    private String employeeUuid;
    private String userUuid;
}
