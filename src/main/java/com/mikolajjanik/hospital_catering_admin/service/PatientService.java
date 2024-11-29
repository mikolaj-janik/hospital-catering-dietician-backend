package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.NewPatientDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;

import java.util.List;

public interface PatientService {
    List<Patient>findPatientsByWardId(Long id, String orderBy);
    List<Patient>findPatientsByHospitalId(Long id);
    Patient registerNewPatient(NewPatientDTO patientDTO);
}
