package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.AdminRepository;
import com.mikolajjanik.hospital_catering_admin.dto.AdminDTO;
import com.mikolajjanik.hospital_catering_admin.dto.LoginUserDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewUserDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Admin;
import com.mikolajjanik.hospital_catering_admin.exception.BadLoginCredentialsException;
import com.mikolajjanik.hospital_catering_admin.exception.PasswordsNotMatchException;
import com.mikolajjanik.hospital_catering_admin.exception.UserAlreadyExistException;
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

    private AdminRepository adminRepository;

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(AdminRepository adminRepository,
                           AuthenticationManager authenticationManager,
                           JWTService jwtService) {
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Override
    @SneakyThrows
    public Admin register(NewUserDTO user) {
        String email = user.getEmail();
        Admin a = adminRepository.findAdminByEmail(email);

        if (a != null) {
            throw new UserAlreadyExistException();
        }

        String password = user.getPassword();
        String repeatedPassword = user.getRepeatedPassword();

        if (!password.equals(repeatedPassword)) {
            throw new PasswordsNotMatchException();
        }

        Admin admin = new Admin();

        admin.setPassword(encoder.encode(password));

        admin.setEmail(user.getEmail());
        admin.setName(user.getName());
        admin.setSurname(user.getSurname());

        return adminRepository.save(admin);
    }

    @Override
    @SneakyThrows
    public String verify(LoginUserDTO user) {
        Admin admin = adminRepository.findAdminByEmail(user.getEmail());

        if (admin == null || !encoder.matches(user.getPassword(), admin.getPassword())) {
            throw new BadLoginCredentialsException();
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        return jwtService.generateToken(user.getEmail());
    }

    @Override
    @SneakyThrows
    public AdminDTO getAdminByEmail(String email) {
        Admin admin = adminRepository.findAdminByEmail(email);
        byte[] profilePicture = adminRepository.findProfilePictureByEmail(email);

        if (admin == null) {
            throw new UserNotFoundException(email);
        }

        return new AdminDTO(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getSurname(),
                Base64.getEncoder().encodeToString(profilePicture));
    }
}
