package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.HistoriaUsuarioDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.HistoriaUsuario}.
 */
public interface HistoriaUsuarioService {

    /**
     * Save a historiaUsuario.
     *
     * @param historiaUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    HistoriaUsuarioDTO save(HistoriaUsuarioDTO historiaUsuarioDTO);

    /**
     * Get all the historiaUsuarios.
     *
     * @return the list of entities.
     */
    List<HistoriaUsuarioDTO> findAll();


    /**
     * Get the "id" historiaUsuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistoriaUsuarioDTO> findOne(Long id);

    /**
     * Delete the "id" historiaUsuario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
