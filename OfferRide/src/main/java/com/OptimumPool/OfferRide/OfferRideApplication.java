package com.OptimumPool.OfferRide;

import com.OptimumPool.OfferRide.Filter.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OfferRideApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferRideApplication.class, args);
		System.out.println("Offerride Running... ");
	}

//	@Bean
//	public FilterRegistrationBean getFilterReg() {
//		FilterRegistrationBean filterReg = new FilterRegistrationBean ();
//		filterReg.setFilter(new Filter());
//		filterReg.addUrlPatterns("/api/users/offerride");
//		return filterReg;
//
//	}



}
