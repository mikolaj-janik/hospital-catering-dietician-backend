package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DietRepository;
import com.mikolajjanik.hospital_catering_admin.dao.MealRepository;
import com.mikolajjanik.hospital_catering_admin.dao.WardRepository;
import com.mikolajjanik.hospital_catering_admin.dto.DietDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateDietDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import com.mikolajjanik.hospital_catering_admin.exception.DietAlreadyExistException;
import com.mikolajjanik.hospital_catering_admin.exception.DietCannotBeDeletedException;
import com.mikolajjanik.hospital_catering_admin.exception.DietNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.WardNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DietServiceImpl implements DietService {
    private final DietRepository dietRepository;
    private final MealRepository mealRepository;
    private final WardRepository wardRepository;

    @Autowired
    public DietServiceImpl(DietRepository dietRepository, MealRepository mealRepository, WardRepository wardRepository) {
        this.dietRepository = dietRepository;
        this.mealRepository = mealRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public Set<Diet> findAll() {
        return dietRepository.findAllByOrderByName();
    }

    @Override
    public Set<Diet> findAllCurrentDiets() {
        Set<Diet> diets = dietRepository.findAllByOrderByName();
        Set<Diet> dietsToReturn = new HashSet<>();

        for (Diet diet : diets) {
            Set<Meal> meals = mealRepository.findMealsByDietId(diet.getId());
            if (!meals.isEmpty()) {
                dietsToReturn.add(diet);
            }
        }
        return dietsToReturn;
    }

    @Override
    @SneakyThrows
    public Diet findDietById(Long id, String diary) {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }

        if (diary.equals("true")) {
            Set<Meal> meals = mealRepository.findMealsByDietId(id);
            if (meals.isEmpty()) {
                throw new DietNotFoundException(id);
            }
        }
        return diet;
    }

    @Override
    @SneakyThrows
    public Diet addDiet(DietDTO dietDTO) {

        String name = dietDTO.getName();
        String description = dietDTO.getDescription();

        name = name.toLowerCase();

        checkDietAlreadyExists(name);

        name = name.toLowerCase();
        
        return dietRepository.save(new Diet(name, description));
    }

    @Override
    @SneakyThrows
    public Diet updateDiet(UpdateDietDTO dietDTO) {
        Long id = dietDTO.getId();
        String name = dietDTO.getName();
        String description = dietDTO.getDescription();

        name = name.toLowerCase();

        checkDietAlreadyExists(id);

        Diet diet = dietRepository.findDietById(id);
        diet.setName(name);
        diet.setDescription(description);

        return dietRepository.save(diet);

    }

    @Override
    public Set<Diet> findDietsByName(String name) {
        return dietRepository.findDietsByNameContainingIgnoreCase(name);
    }

    @Override
    @SneakyThrows
    public Set<Diet> findDietsByWardId(Long id) {
        Ward ward = wardRepository.findWardById(id);

        if (ward == null) {
            throw new WardNotFoundException(id);
        }
        return dietRepository.findDietsByWardId(id);
    }

    @Override
    @SneakyThrows
    public void deleteDietById(Long id) {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }

        Set<Meal> meals = mealRepository.findMealsByDietId(id);

        if (!meals.isEmpty()) {
            throw new DietCannotBeDeletedException();
        }

        dietRepository.deleteById(id);
    }

    private void checkDietAlreadyExists(String name) throws IOException {
        Diet diet = dietRepository.findDietByName(name);

        if (diet != null) {
            throw new DietAlreadyExistException();
        }
    }

    private void checkDietAlreadyExists(Long id) throws IOException {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }
    }
}
