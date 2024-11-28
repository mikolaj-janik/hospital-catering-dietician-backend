package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DieticianRepository;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.entity.UserPrincipal;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final DieticianRepository dieticianRepository;

    @Autowired
    public MyUserDetailsService(DieticianRepository dieticianRepository) {
        this.dieticianRepository = dieticianRepository;
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Dietician dietician = dieticianRepository.findDieticianByEmail(email);
        return new UserPrincipal(dietician);
    }
}
