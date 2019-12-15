package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.PerfilDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Perfil}.
 */
public interface PerfilService {

    /**
     * Save a perfil.
     *
     * @param perfilDTO the entity to save.
     * @return the persisted entity.
     */
    PerfilDTO save(PerfilDTO perfilDTO);

    /**
     * Get all the perfils.
     *
     * @return the list of entities.
     */
    List<PerfilDTO> findAll();


    /**
     * Get the "id" perfil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PerfilDTO> findOne(Long id);

    /**
     * Delete the "id" perfil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
