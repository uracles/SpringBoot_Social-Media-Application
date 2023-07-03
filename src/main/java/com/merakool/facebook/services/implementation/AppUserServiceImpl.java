package com.merakool.facebook.services.implementation;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.repository.AppUserRepository;
import com.merakool.facebook.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser registerUser(AppUser newUser) {
        if (appUserRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        //TRY COMMENT OUT THIS LINES OF CODE AS I HAVE ALREADY ADDED TO STRING MTHD IN THE ENTITY PACKAGE
        String genderValue = newUser.getGender().toString();

        // Set the converted gender value
        newUser.setGender(genderValue);

        // Save the user entity in the database
        return appUserRepository.save(newUser);
    }

    @Override
    public Optional<AppUser> loginUser(String email, String password) {
        // Find the user by email
        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);

        // Validate the user's credentials
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (password.equals(user.getPassword())) {
                return optionalUser;
            }
        }

        return Optional.empty();
    }
}