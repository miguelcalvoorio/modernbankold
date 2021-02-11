package org.modernbank.architecture.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import org.modernbank.architecture.core.exception.ApiExceptionHandler;

@ConditionalOnProperty(value = "modernbank.apiExceptionHandler.enabled", havingValue = "true")
public class ExceptionHandlerAutoConfiguration {

    @Bean
	ApiExceptionHandler getApiExceptionHandler(){
		return new ApiExceptionHandler();
	}
}
