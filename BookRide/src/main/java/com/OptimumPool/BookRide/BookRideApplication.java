package com.OptimumPool.BookRide;

import com.OptimumPool.BookRide.Filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookRideApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRideApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new JwtFilter());
		bean.addUrlPatterns("/bookrides/*", "/booking/*", "/invoice/*", "/rides/*");
		return bean;
	}
}