package com.merakool.facebook.services;

import com.merakool.facebook.entities.AppUser;

import java.util.Optional;

public interface AppUserService {

    AppUser registerUser(AppUser newUser);

    Optional<AppUser> loginUser(String email, String password);
}
