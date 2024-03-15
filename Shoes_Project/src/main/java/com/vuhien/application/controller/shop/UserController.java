package com.vuhien.application.controller.shop;

import com.vuhien.application.entity.User;
import com.vuhien.application.exception.BadRequestException;
import com.vuhien.application.model.dto.UserDTO;
import com.vuhien.application.model.mapper.UserMapper;
import com.vuhien.application.model.request.*;
import com.vuhien.application.security.CustomUserDetails;
import com.vuhien.application.security.JwtTokenUtil;
import com.vuhien.application.service.AuthService;
import com.vuhien.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//import static com.vuhien.application.config.Constant.MAX_AGE_COOKIE;
import static com.vuhien.application.config.Contant.MAX_AGE_COOKIE;

@Controller
//@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;


    @PostMapping("/api/forgot-password")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.sendPasswordResetToken(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok("Email khôi phục mật khẩu đã được gửi");
    }

    @PostMapping("/api/reset-password")  // Chỉnh sửa ở đây để sử dụng phương thức POST
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công, trở lại trang chủ để đăng nhập");
    }

    @GetMapping("/api/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        // Kiểm tra xem token có hợp lệ không và thực hiện xử lý tương ứng
        // ...

        // Truyền token vào model để sử dụng trong trang Thymeleaf
        model.addAttribute("resetToken", token);

        // Trả về trang Thymeleaf "shop/doimatkhau_email"
        return "shop/doimatkhau_email";
    }


    @GetMapping("/users")
    public ResponseEntity<Object> getListUsers() {
        List<UserDTO> userDTOS = userService.getListUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @PostMapping("/api/admin/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        return ResponseEntity.ok(UserMapper.toUserDTO(user));
    }

    @PostMapping("/api/register")
    public ResponseEntity<Object> register(@Valid @RequestBody CreateUserRequest createUserRequest, HttpServletResponse response) {
        // Create user
        User user = userService.createUser(createUserRequest);

        // Generate token
        UserDetails principal = new CustomUserDetails(user);
        String token = jwtTokenUtil.generateToken(principal);

        // Add token to cookie for login
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Include OTP in the response
        return ResponseEntity.ok(Map.of("fullName", user.getFullName(), "otp", user.getOtp()));
    }


    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        // Authenticate
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));
            // Generate token
            String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

            // Add token to cookie for login
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setMaxAge(MAX_AGE_COOKIE);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(UserMapper.toUserDTO(((CustomUserDetails) authentication.getPrincipal()).getUser()));
        } catch (Exception ex) {
            throw new BadRequestException("Email or password is incorrect");
        }
    }

    @GetMapping("/tai-khoan")
    public String getProfilePage(Model model) {
        return "shop/account";
    }

    @PostMapping("/api/change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordRequest passwordReq) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        userService.changePassword(user, passwordReq);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PutMapping("/api/update-profile")
    public ResponseEntity<Object> updateProfile(@Valid @RequestBody UpdateProfileRequest profileReq) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        user = userService.updateProfile(user, profileReq);
        UserDetails userDetails = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Profile updated successfully");
    }

    @PostMapping("/api/resend-verification-token")
    public ResponseEntity<Object> resendVerificationToken() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        // Resend verification token
        userService.sendVerificationToken(user);

        return ResponseEntity.ok("Verification token resent successfully");
    }

    @GetMapping("/api/verify-user")
    public String verifyUser(@RequestParam String token, Model model) {
        if (userService.verifyUser(token)) {
            model.addAttribute("verificationMessage", "Người dùng đã xác minh thành công. Bây giờ bạn có thể đăng nhập.");
        } else {
            model.addAttribute("verificationMessage", "Mã thông báo xác minh không hợp lệ hoặc đã hết hạn.");
        }
        return "shop/verification"; // Trả về trang thông báo xác minh
    }



}
