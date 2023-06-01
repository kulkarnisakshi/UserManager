package com.sakshicodes.UserManager.service;

import com.sakshicodes.UserManager.model.UserDtls;

public interface UserService {

    public UserDtls createUser(UserDtls user);

    public boolean checkEmail(String email);
}
