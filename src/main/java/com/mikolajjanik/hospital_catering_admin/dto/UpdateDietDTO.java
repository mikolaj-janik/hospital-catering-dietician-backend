package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class UpdateDietDTO {

    @Min(value = 1, message = "Field 'id' must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "Field 'id' is too big")
    @NotNull(message = "Field 'id' cannot be null.")
    private Long id;

    @NotBlank(message = "Field 'name' cannot be empty.")
    @NotNull(message = "Field 'name' cannot be null.")
    @Size(max = 120, message = "Field 'name' cannot exceed 120 characters.")
    private String name;

    @NotBlank(message = "Field 'description' cannot be empty.")
    @NotNull(message = "Field 'description' cannot be null.")
    @Size(max = 1020, message = "Field 'description' cannot exceed 1020 characters.")
    private String description;
}
