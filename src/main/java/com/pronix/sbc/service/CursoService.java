package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.CursoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.Curso}.
 */
public interface CursoService {

    /**
     * Save a curso.
     *
     * @param cursoDTO the entity to save.
     * @return the persisted entity.
     */
    CursoDTO save(CursoDTO cursoDTO);

    /**
     * Get all the cursos.
     *
     * @return the list of entities.
     */
    List<CursoDTO> findAll();


    /**
     * Get the "id" curso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CursoDTO> findOne(Long id);

    /**
     * Delete the "id" curso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
