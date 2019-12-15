package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.SuscripcionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Suscripcion}.
 */
public interface SuscripcionService {

    /**
     * Save a suscripcion.
     *
     * @param suscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    SuscripcionDTO save(SuscripcionDTO suscripcionDTO);

    /**
     * Get all the suscripcions.
     *
     * @return the list of entities.
     */
    List<SuscripcionDTO> findAll();

    /**
     * Get all the suscripcions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SuscripcionDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" suscripcion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SuscripcionDTO> findOne(Long id);

    /**
     * Delete the "id" suscripcion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
