package com.vuhien.application.service;

import com.vuhien.application.entity.User;
import com.vuhien.application.model.dto.UserDTO;
import com.vuhien.application.model.request.ChangePasswordRequest;
import com.vuhien.application.model.request.CreateUserRequest;
import com.vuhien.application.model.request.UpdateProfileRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getListUsers();

    Page<User> adminListUserPages(String fullName, String phone, String email, Integer page);

    User createUser(CreateUserRequest createUserRequest);

    void changePassword(User user, ChangePasswordRequest changePasswordRequest);

    User updateProfile(User user, UpdateProfileRequest updateProfileRequest);

    // Các phương thức mới
    void sendOtp(User user);

    boolean verifyOtp(User user, String otp);

    void sendVerificationToken(User user);

    boolean verifyUser(String token);

    String resendVerificationToken(User user);

}
