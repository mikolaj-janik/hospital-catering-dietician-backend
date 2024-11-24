package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/ward/{id}")
    public ResponseEntity<List<Patient>> getPatientsByWardId(@PathVariable("id") Long wardId) {
        List<Patient> patients = patientService.findPatientsByWardId(wardId);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<List<Patient>> getPatientsByHospitalId(@PathVariable("id") Long hospitalId) {
        List<Patient> patients = patientService.findPatientsByHospitalId(hospitalId);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

}
