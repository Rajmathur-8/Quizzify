package com.raj.quiz_app_backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvLoader {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))  // looks in your project root
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        //System.out.println("✅ Loaded .env successfully. MAIL_USERNAME = " + dotenv.get("MAIL_USERNAME"));
    }
}
