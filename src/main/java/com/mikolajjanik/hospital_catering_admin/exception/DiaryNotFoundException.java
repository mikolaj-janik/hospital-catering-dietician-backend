package com.mikolajjanik.hospital_catering_admin.exception;

import java.io.IOException;

public class DiaryNotFoundException extends IOException {
    public DiaryNotFoundException(Long id) {
        super("No diary found with id: " + id);
    }
}
