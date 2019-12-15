package com.pronix.sbc.service;

import com.pronix.sbc.service.dto.PuestoDeTrabajoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pronix.sbc.domain.PuestoDeTrabajo}.
 */
public interface PuestoDeTrabajoService {

    /**
     * Save a puestoDeTrabajo.
     *
     * @param puestoDeTrabajoDTO the entity to save.
     * @return the persisted entity.
     */
    PuestoDeTrabajoDTO save(PuestoDeTrabajoDTO puestoDeTrabajoDTO);

    /**
     * Get all the puestoDeTrabajos.
     *
     * @return the list of entities.
     */
    List<PuestoDeTrabajoDTO> findAll();

    /**
     * Get all the puestoDeTrabajos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PuestoDeTrabajoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" puestoDeTrabajo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PuestoDeTrabajoDTO> findOne(Long id);

    /**
     * Delete the "id" puestoDeTrabajo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
