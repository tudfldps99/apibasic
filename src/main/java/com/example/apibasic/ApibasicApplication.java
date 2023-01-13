package com.example.apibasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApibasicApplication {

	public static void main(String[] args) {
		// 주석
		int a = 10;
		System.out.println(a);
		System.out.println();
		SpringApplication.run(ApibasicApplication.class, args);
	}

}
