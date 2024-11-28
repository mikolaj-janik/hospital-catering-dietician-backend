package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DieticianRepository;
import com.mikolajjanik.hospital_catering_admin.dto.UserDTO;
import com.mikolajjanik.hospital_catering_admin.dto.LoginUserDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.exception.BadLoginCredentialsException;
import com.mikolajjanik.hospital_catering_admin.exception.UserNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {

    private final DieticianRepository dieticianRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(DieticianRepository dieticianRepository,
                           AuthenticationManager authenticationManager,
                           JWTService jwtService) {
        this.dieticianRepository = dieticianRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @SneakyThrows
    public String verify(LoginUserDTO user) {
        Dietician dietician = dieticianRepository.findDieticianByEmail(user.getEmail());

        if (dietician == null || !encoder.matches(user.getPassword(), dietician.getPassword())) {
            throw new BadLoginCredentialsException();
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        return jwtService.generateToken(user.getEmail());
    }

    @Override
    @SneakyThrows
    public UserDTO getUserByEmail(String email) {
        Dietician dietician = dieticianRepository.findDieticianByEmail(email);
        byte[] profilePicture = dieticianRepository.findProfilePictureByEmail(email);

        if (dietician == null) {
            throw new UserNotFoundException(email);
        }

        return new UserDTO(
                dietician.getId(),
                dietician.getEmail(),
                dietician.getName(),
                dietician.getSurname(),
                Base64.getEncoder().encodeToString(profilePicture));
    }
}
