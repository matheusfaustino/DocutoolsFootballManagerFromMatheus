package com.docutools.matheus.footballmanager;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class FootballManagerSwaggerConfig {
	@Autowired
	private FootballManagerProperties properties;

	@Bean
	public Docket footballManagerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Lists.newArrayList(this.securityContext()))
				.securitySchemes(Lists.newArrayList(this.apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage(this.getClass().getPackage().getName()))
				.build()
				.apiInfo(this.metadata());
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title(properties.getProjectName())
				.description(properties.getProjectDescription())
				.version(properties.getProjectVersion())
				.build();
	}


	private ApiKey apiKey() {
		return new ApiKey("JWT", this.properties.getAuthTokenHeader(), "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/api/.*"))
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(
				new SecurityReference("JWT", authorizationScopes));
	}
}
