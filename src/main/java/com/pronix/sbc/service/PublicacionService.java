package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.PublicacionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Publicacion}.
 */
public interface PublicacionService {

    /**
     * Save a publicacion.
     *
     * @param publicacionDTO the entity to save.
     * @return the persisted entity.
     */
    PublicacionDTO save(PublicacionDTO publicacionDTO);

    /**
     * Get all the publicacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" publicacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicacionDTO> findOne(Long id);

    /**
     * Delete the "id" publicacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
