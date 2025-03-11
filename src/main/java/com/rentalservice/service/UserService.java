package com.rentalservice.service;

import com.rentalservice.model.AccessToken;
import com.rentalservice.model.entity.User;

public interface UserService {
    User getByEmail(String email);
    User saveUser(User user);
    AccessToken authenticate(String email, String password);

}
