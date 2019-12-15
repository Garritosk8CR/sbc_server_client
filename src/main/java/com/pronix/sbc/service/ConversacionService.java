package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.ConversacionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Conversacion}.
 */
public interface ConversacionService {

    /**
     * Save a conversacion.
     *
     * @param conversacionDTO the entity to save.
     * @return the persisted entity.
     */
    ConversacionDTO save(ConversacionDTO conversacionDTO);

    /**
     * Get all the conversacions.
     *
     * @return the list of entities.
     */
    List<ConversacionDTO> findAll();


    /**
     * Get the "id" conversacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConversacionDTO> findOne(Long id);

    /**
     * Delete the "id" conversacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
