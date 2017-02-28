package com.devopsbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.devopsbuddy.backend.service.EmailService;
import com.devopsbuddy.backend.service.SmtpEmailService;

@Configuration
@Profile("prod")
@PropertySource("file:///C:/dados/Projetos/SpringBoot_AWS/devopsbuddy_config/application-prod.properties")
public class ProductionConfiguration {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
