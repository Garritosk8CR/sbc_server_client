package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.RequisitoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Requisito}.
 */
public interface RequisitoService {

    /**
     * Save a requisito.
     *
     * @param requisitoDTO the entity to save.
     * @return the persisted entity.
     */
    RequisitoDTO save(RequisitoDTO requisitoDTO);

    /**
     * Get all the requisitos.
     *
     * @return the list of entities.
     */
    List<RequisitoDTO> findAll();


    /**
     * Get the "id" requisito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequisitoDTO> findOne(Long id);

    /**
     * Delete the "id" requisito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
