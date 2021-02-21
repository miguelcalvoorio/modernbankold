package org.modernbank.architecture.core;

import org.modernbank.architecture.core.exception.AccessDeniedFailureHandler;
import org.modernbank.architecture.core.exception.ApiExceptionHandler;
import org.modernbank.architecture.core.exception.AuthenticationEntryPointFailureHandler;
import org.springframework.context.annotation.Bean;

public class ArchitectureCoreAutoConfiguration {

    @Bean
	ApiExceptionHandler getApiExceptionHandler() {
		return new ApiExceptionHandler();
	}

	@Bean
	AccessDeniedFailureHandler getAccessDeniedFailureHandler() {
		return new AccessDeniedFailureHandler();
	}

	@Bean
	AuthenticationEntryPointFailureHandler getAuthenticationEntryPointFailureHandler() {
		return new AuthenticationEntryPointFailureHandler();
	}
}
