package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DietDTO {

    @NotBlank(message = "Field 'name' cannot be empty.")
    @NotNull(message = "Field 'name' cannot be null.")
    @Size(max = 120, message = "Field 'name' cannot exceed 120 characters.")
    private String name;

    @NotBlank(message = "Field 'description' cannot be empty.")
    @NotNull(message = "Field 'description' cannot be null.")
    @Size(max = 1020, message = "Field 'description' cannot exceed 1020 characters.")
    private String description;

}
