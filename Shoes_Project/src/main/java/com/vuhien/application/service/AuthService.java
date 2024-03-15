package com.vuhien.application.service;

public interface AuthService {
    void sendPasswordResetToken(String email);
    void resetPassword(String token, String newPassword);
}

