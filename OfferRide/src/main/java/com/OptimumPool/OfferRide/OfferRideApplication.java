package com.OptimumPool.OfferRide;

import com.OptimumPool.OfferRide.Filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OfferRideApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferRideApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter filter) {
		FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>(filter);
		// FIXED: added "/offerride" — the wildcard "/offerride/*" misses POST /offerride itself
		bean.addUrlPatterns("/offerride", "/offerride/*");
		return bean;
	}
}