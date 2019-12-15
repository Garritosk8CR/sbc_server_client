package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.CarreraProfesionalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.CarreraProfesional}.
 */
public interface CarreraProfesionalService {

    /**
     * Save a carreraProfesional.
     *
     * @param carreraProfesionalDTO the entity to save.
     * @return the persisted entity.
     */
    CarreraProfesionalDTO save(CarreraProfesionalDTO carreraProfesionalDTO);

    /**
     * Get all the carreraProfesionals.
     *
     * @return the list of entities.
     */
    List<CarreraProfesionalDTO> findAll();


    /**
     * Get the "id" carreraProfesional.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarreraProfesionalDTO> findOne(Long id);

    /**
     * Delete the "id" carreraProfesional.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
