package com.docutools.matheus.footballmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class FootballManagerConfiguration {
	@Bean
	public RequestMappingHandlerMapping useTrailingSlash() {
		return new RequestMappingHandlerMapping() {{setUseTrailingSlashMatch(false);}};
	}
}
