package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class DieticianNotFoundException extends IOException {
    public DieticianNotFoundException(Long id) {
        super("No dietician found with id: " + id);
    }
}
