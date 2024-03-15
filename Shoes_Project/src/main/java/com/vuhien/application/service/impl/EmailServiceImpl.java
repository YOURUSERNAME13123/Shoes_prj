package com.vuhien.application.service.impl;

import com.vuhien.application.entity.User;
import com.vuhien.application.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @Override
    public void sendOtpEmail(String to, String otp) {
        String subject = "OTP Verification";
        String body = "Your OTP is: " + otp;
        sendEmail(to, subject, body);
    }
}
