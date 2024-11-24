package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.AdminDTO;
import com.mikolajjanik.hospital_catering_admin.dto.LoginUserDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewUserDTO;
import com.mikolajjanik.hospital_catering_admin.dto.TokenDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Admin;
import com.mikolajjanik.hospital_catering_admin.service.JWTService;
import com.mikolajjanik.hospital_catering_admin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private JWTService jwtService;
    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @PostMapping("/register")
    public ResponseEntity<Admin> register(@Valid @RequestBody NewUserDTO user) {
        Admin admin = userService.register(user);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginUserDTO user) {
        TokenDTO tokenDTO = new TokenDTO();
        String token = userService.verify(user);
        tokenDTO.setToken(token);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@Valid @RequestBody TokenDTO tokenDTO) {
        TokenDTO refreshedToken = jwtService.refreshToken(tokenDTO);
        return new ResponseEntity<>(refreshedToken, HttpStatus.OK);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<AdminDTO> getAdminByEmail(@PathVariable String email) {
        AdminDTO admin = userService.getAdminByEmail(email);
        return new ResponseEntity<AdminDTO>(admin, HttpStatus.OK);
    }
}
