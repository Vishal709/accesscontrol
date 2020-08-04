package com.demo.accesscontrol;

import java.awt.image.BufferedImage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
// @EnableJpaAuditing
public class AccesscontroleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccesscontroleApplication.class, args);
	}
//	@Bean
//	 public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
//	     return new BufferedImageHttpMessageConverter();
//	 }

}
