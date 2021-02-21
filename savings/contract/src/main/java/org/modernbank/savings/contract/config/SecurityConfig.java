package org.modernbank.savings.contract.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.modernbank.architecture.core.exception.AccessDeniedFailureHandler;
import org.modernbank.architecture.core.exception.AuthenticationEntryPointFailureHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedFailureHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPointFailureHandler authenticationEntryPointHandler;

    @Value("${modernbank.security.enabled}")
    private boolean securityEnabled = false;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!securityEnabled) {
            http.authorizeRequests().antMatchers("/").permitAll();
        } else {
            http.cors();
            
            // Allow access
            http.authorizeRequests().antMatchers("/actuator/health").permitAll();
            http.authorizeRequests().antMatchers("/v2/api-docs",
                                                 "/swagger-ui/**",
                                                 "/swagger-resources/**").permitAll();
            
            // Secure access
            http.authorizeRequests().antMatchers("/savings/**").hasAnyAuthority("SCOPE_client", "SCOPE_employee");
            http.authorizeRequests().anyRequest().authenticated();
            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler);
            http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
            http.oauth2ResourceServer().jwt();
        }
    }
}