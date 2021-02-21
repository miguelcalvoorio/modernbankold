package org.modernbank.architecture.core.security;

import java.util.List;
import java.util.Map;

import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderValidations {
    private static final String SCOPE = "scope";

    private HeaderValidations() {}
    
    public static RequesterData getRequesterData() {
        Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RequesterData requesterData = new RequesterData();
        requesterData.setUuid(jwt.getClaimAsString("sub"));
        requesterData.setUserName(jwt.getClaimAsString("preferred_username"));
        requesterData.setFormalName(jwt.getClaimAsString("name"));
        requesterData.setFirstName(jwt.getClaimAsString("given_name"));
        requesterData.setLastName(jwt.getClaimAsString("family_name"));

        if (jwt.getClaimAsString(SCOPE).indexOf("user") != -1) requesterData.setScope(RequesterData.Scope.USER);
        if (jwt.getClaimAsString(SCOPE).indexOf("client") != -1) requesterData.setScope(RequesterData.Scope.CLIENT);
        if (jwt.getClaimAsString(SCOPE).indexOf("employee") != -1) requesterData.setScope(RequesterData.Scope.EMPLOYEE);

        switch (requesterData.getScope()) {
            case USER:
                requesterData.setUserUuid(requesterData.getUuid());
                break;
            case CLIENT:
                requesterData.setClientUuid(requesterData.getUuid());
                break;
            case EMPLOYEE:
                requesterData.setEmployeeUuid(requesterData.getUuid());
                break;
        }

        try {
            Map<String, Object> realmAccess = (HashMap<String, Object>)jwt.getClaims().get("realm_access");
            List<String> rolesList = (List<String>)realmAccess.get("roles");
            requesterData.setRolesList(rolesList);
        } catch(Exception ex) {
            log.warn("Cannot get realAccess roles from token: " + ex.getMessage());
        }
        
        return requesterData;
    }

    public static void processSecurityHeader() {
        // TO DO
    }
}
