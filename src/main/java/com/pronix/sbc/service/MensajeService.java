package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.MensajeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Mensaje}.
 */
public interface MensajeService {

    /**
     * Save a mensaje.
     *
     * @param mensajeDTO the entity to save.
     * @return the persisted entity.
     */
    MensajeDTO save(MensajeDTO mensajeDTO);

    /**
     * Get all the mensajes.
     *
     * @return the list of entities.
     */
    List<MensajeDTO> findAll();


    /**
     * Get the "id" mensaje.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MensajeDTO> findOne(Long id);

    /**
     * Delete the "id" mensaje.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
