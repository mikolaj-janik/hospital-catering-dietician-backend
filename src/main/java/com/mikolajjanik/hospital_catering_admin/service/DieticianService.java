package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.DieticianDTO;
import com.mikolajjanik.hospital_catering_admin.dto.DieticianDetailsDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface DieticianService {
    DieticianDetailsDTO findDieticianById(Long id);
    Set<Dietician> findDieticiansByHospitalId(Long id);
    Set<Dietician> findDieticiansByWardId(Long id);
    List<Dietician> findAllDieticians(Long hospitalId);
    Dietician registerNewDietician(DieticianDTO dieticianDTO);
    DieticianDetailsDTO uploadProfilePicture(Long id, MultipartFile picture);
    void deleteDieticianById(Long id);
}
