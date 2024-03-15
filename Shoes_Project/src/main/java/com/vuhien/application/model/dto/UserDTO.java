package com.vuhien.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private long id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private String avatar;
    //private String password;
    private List<String> roles;

    public void setId(long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }
}
