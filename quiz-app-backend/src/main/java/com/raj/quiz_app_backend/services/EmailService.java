package com.raj.quiz_app_backend.services;

import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String htmlContent); // send email
    String loadTemplate(String templateName, Map<String, String> placeholders); // parse template
}
