package com.mikolajjanik.hospital_catering_admin.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String profilePicture;

    public AdminDTO(Long id, String email, String name, String surname, String profilePicture) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
    }
}
