package com.raj.quiz_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizzifyAnAdvancedQuizAppApplication {

	public static void main(String[] args) {
		//System.out.println("MAIL_USERNAME from System.getenv() = " + System.getenv("MAIL_USERNAME"));
		SpringApplication.run(QuizzifyAnAdvancedQuizAppApplication.class, args);
	}

}
