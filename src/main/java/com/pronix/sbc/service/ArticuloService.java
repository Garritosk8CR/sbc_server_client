package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.ArticuloDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Articulo}.
 */
public interface ArticuloService {

    /**
     * Save a articulo.
     *
     * @param articuloDTO the entity to save.
     * @return the persisted entity.
     */
    ArticuloDTO save(ArticuloDTO articuloDTO);

    /**
     * Get all the articulos.
     *
     * @return the list of entities.
     */
    List<ArticuloDTO> findAll();


    /**
     * Get the "id" articulo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticuloDTO> findOne(Long id);

    /**
     * Delete the "id" articulo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
