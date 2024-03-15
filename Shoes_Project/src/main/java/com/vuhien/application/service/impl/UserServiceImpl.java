package com.vuhien.application.service.impl;

import com.vuhien.application.entity.User;
import com.vuhien.application.exception.BadRequestException;
import com.vuhien.application.exception.NotFoundException;
import com.vuhien.application.model.dto.UserDTO;
import com.vuhien.application.model.mapper.UserMapper;
import com.vuhien.application.model.request.ChangePasswordRequest;
import com.vuhien.application.model.request.CreateUserRequest;
import com.vuhien.application.model.request.UpdateProfileRequest;
import com.vuhien.application.repository.UserRepository;
import com.vuhien.application.service.EmailService;
import com.vuhien.application.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.vuhien.application.config.Contant.LIMIT_USER;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;



    @Override
    public List<UserDTO> getListUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(UserMapper.toUserDTO(user));
        }
        return userDTOS;
    }

    @Override
    public Page<User> adminListUserPages(String fullName, String phone, String email, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, LIMIT_USER, Sort.by("created_at").descending());
        return userRepository.adminListUserPages(fullName, phone, email, pageable);
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        User user = userRepository.findByEmail(createUserRequest.getEmail());
        if (user != null) {
            throw new BadRequestException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác!");
        }
        user = UserMapper.toUser(createUserRequest);
        userRepository.save(user);

        // Gửi mã OTP
        sendOtp(user);

        return user;
    }


    @Override
    public void changePassword(User user, ChangePasswordRequest changePasswordRequest) {
        // Kiểm tra mật khẩu cũ
        if (!BCrypt.checkpw(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        // Kiểm tra token reset (nếu tồn tại)
        if (StringUtils.isNotBlank(user.getResetToken())) {
            throw new BadRequestException("Vui lòng hoàn thành quá trình khôi phục mật khẩu trước khi đổi mật khẩu");
        }

        // Tiếp tục với phần còn lại của đổi mật khẩu
        String hash = BCrypt.hashpw(changePasswordRequest.getNewPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        userRepository.save(user);
    }


    @Override
    public User updateProfile(User user, UpdateProfileRequest updateProfileRequest) {
        user.setFullName(updateProfileRequest.getFullName());
        user.setPhone(updateProfileRequest.getPhone());
        user.setAddress(updateProfileRequest.getAddress());

        return userRepository.save(user);
    }

    @Override
    public void sendOtp(User user) {
        String otp = generateOtp();
        user.setOtp(otp);
        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);
    }

    @Override
    public boolean verifyOtp(User user, String otp) {
        return Objects.equals(user.getOtp(), otp);
    }

    @Override
    public void sendVerificationToken(User user) {
        String token = generateOtp(); // Tạo mã OTP mới hoặc token theo yêu cầu của bạn
        user.setOtp(token); // Lưu token vào trường OTP hoặc trường phù hợp của user
        userRepository.save(user);

        // Tạo đường dẫn xác minh với token
        String verificationUrl = "http://localhost/api/verify-user?token=" + token;

        // Gửi email với đường dẫn xác minh
        String subject = "Verification Token";
        String body = "Click on the following link to verify your account: " + verificationUrl;
        emailService.sendEmail(user.getEmail(), subject, body);
    }



    @Override
    public boolean verifyUser(String token) {
        Optional<User> optionalUser = userRepository.findByOtp(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setOtp(null); // Đặt giá trị OTP về null để xác minh chỉ thực hiện được một lần
            userRepository.save(user);
            return true;
        }
        return false;
    }



    @Override
    public String resendVerificationToken(User user) {
        // Tạo mã OTP mới
        String newOtp = generateOtp();

        // Lưu mã OTP mới vào cơ sở dữ liệu
        user.setOtp(newOtp);
        userRepository.save(user);

        // Gửi email với mã OTP mới
        emailService.sendOtpEmail(user.getEmail(), newOtp);

        return newOtp;
    }



    // Tự định nghĩa hàm để tạo mã OTP mới
    private String generateOtp() {
        int otpLength = 6; // Độ dài của mã OTP
        StringBuilder otp = new StringBuilder();

        // Dãy ký tự cho việc tạo mã OTP (các chữ số từ 0 đến 9)
        String digits = "0123456789";

        // Sử dụng java.util.Random để tạo mã OTP ngẫu nhiên
        Random random = new Random();

        for (int i = 0; i < otpLength; i++) {
            int index = random.nextInt(digits.length());
            char digit = digits.charAt(index);
            otp.append(digit);
        }

        return otp.toString();
    }
}
