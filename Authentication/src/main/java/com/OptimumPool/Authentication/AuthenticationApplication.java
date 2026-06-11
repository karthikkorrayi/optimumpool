package com.OptimumPool.Authentication;

import com.OptimumPool.Authentication.Filter.Filter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.FilterRegistration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
		System.out.println("Start!...");

	}

	@Bean
	public FilterRegistrationBean getFilterReg(){
		FilterRegistrationBean filterReg = new FilterRegistrationBean();
		filterReg.setFilter(new Filter());
		filterReg.addUrlPatterns("/login/profile");
		return filterReg;
	}

}
