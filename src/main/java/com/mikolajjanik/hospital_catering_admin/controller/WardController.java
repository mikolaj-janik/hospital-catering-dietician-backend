package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.NewWardDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateDietDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateWardDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import com.mikolajjanik.hospital_catering_admin.service.DieticianService;
import com.mikolajjanik.hospital_catering_admin.service.WardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/wards")
public class WardController {
    private final WardService wardService;
    private final DieticianService dieticianService;

    @Autowired
    public WardController(WardService wardService, DieticianService dieticianService) {
        this.wardService = wardService;
        this.dieticianService = dieticianService;
    }

    @GetMapping("")
    public ResponseEntity<List<Ward>> getWardByDieticianId(@RequestParam("dieticianId") Long id) {
        List<Ward> wards = wardService.findWardsByDieticianId(id);
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ward> getWardById(@PathVariable("id") Long id) {
        Ward ward = wardService.findWardById(id);
        return new ResponseEntity<>(ward, HttpStatus.OK);
    }

    @GetMapping("/{id}/dieticians")
    public ResponseEntity<Set<Dietician>> getDieticiansByWardId(@PathVariable("id") Long id) {
        Set<Dietician> dieticians = dieticianService.findDieticiansByWardId(id);
        return new ResponseEntity<>(dieticians, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Ward> createWard(@Valid @RequestBody NewWardDTO wardDTO) {
        Ward ward = wardService.createWard(wardDTO);
        return new ResponseEntity<>(ward, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Ward> updateWard(@Valid @RequestBody UpdateWardDTO wardDTO) {
        Ward ward = wardService.updateWard(wardDTO);
        return new ResponseEntity<>(ward, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> uploadPicture(@PathVariable("id") Long id) {
        wardService.deleteWardById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
