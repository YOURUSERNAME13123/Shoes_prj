package com.vuhien.application.service.impl;

import com.vuhien.application.entity.User;
import com.vuhien.application.exception.BadRequestException;
import com.vuhien.application.exception.NotFoundException;
import com.vuhien.application.repository.UserRepository;
import com.vuhien.application.service.AuthService;
import com.vuhien.application.service.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Người dùng không tồn tại");
        }

        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        userRepository.save(user);

        String resetUrl = "http://localhost/api/reset-password?token=" + resetToken;
        String subject = "Khôi phục mật khẩu";
        String body = "Click vào liên kết sau để đặt lại mật khẩu của bạn: " + resetUrl;
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user == null) {
            throw new BadRequestException("Token không hợp lệ");
        }

        String hash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        user.setPassword(hash);
        user.setResetToken(null);
        userRepository.save(user);
    }

    private String generateResetToken() {
        int tokenLength = 32; // Độ dài mong muốn của token
        return RandomStringUtils.randomAlphanumeric(tokenLength);
    }

}

