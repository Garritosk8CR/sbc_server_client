package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.PuestoDeTrabajoService;
import com.pronix.sbc.domain.PuestoDeTrabajo;
import com.pronix.sbc.repository.PuestoDeTrabajoRepository;
import com.pronix.sbc.service.dto.PuestoDeTrabajoDTO;
import com.pronix.sbc.service.mapper.PuestoDeTrabajoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PuestoDeTrabajo}.
 */
@Service
@Transactional
public class PuestoDeTrabajoServiceImpl implements PuestoDeTrabajoService {

    private final Logger log = LoggerFactory.getLogger(PuestoDeTrabajoServiceImpl.class);

    private final PuestoDeTrabajoRepository puestoDeTrabajoRepository;

    private final PuestoDeTrabajoMapper puestoDeTrabajoMapper;

    public PuestoDeTrabajoServiceImpl(PuestoDeTrabajoRepository puestoDeTrabajoRepository, PuestoDeTrabajoMapper puestoDeTrabajoMapper) {
        this.puestoDeTrabajoRepository = puestoDeTrabajoRepository;
        this.puestoDeTrabajoMapper = puestoDeTrabajoMapper;
    }

    /**
     * Save a puestoDeTrabajo.
     *
     * @param puestoDeTrabajoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PuestoDeTrabajoDTO save(PuestoDeTrabajoDTO puestoDeTrabajoDTO) {
        log.debug("Request to save PuestoDeTrabajo : {}", puestoDeTrabajoDTO);
        PuestoDeTrabajo puestoDeTrabajo = puestoDeTrabajoMapper.toEntity(puestoDeTrabajoDTO);
        puestoDeTrabajo = puestoDeTrabajoRepository.save(puestoDeTrabajo);
        return puestoDeTrabajoMapper.toDto(puestoDeTrabajo);
    }

    /**
     * Get all the puestoDeTrabajos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PuestoDeTrabajoDTO> findAll() {
        log.debug("Request to get all PuestoDeTrabajos");
        return puestoDeTrabajoRepository.findAllWithEagerRelationships().stream()
            .map(puestoDeTrabajoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the puestoDeTrabajos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PuestoDeTrabajoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return puestoDeTrabajoRepository.findAllWithEagerRelationships(pageable).map(puestoDeTrabajoMapper::toDto);
    }
    

    /**
     * Get one puestoDeTrabajo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PuestoDeTrabajoDTO> findOne(Long id) {
        log.debug("Request to get PuestoDeTrabajo : {}", id);
        return puestoDeTrabajoRepository.findOneWithEagerRelationships(id)
            .map(puestoDeTrabajoMapper::toDto);
    }

    /**
     * Delete the puestoDeTrabajo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PuestoDeTrabajo : {}", id);
        puestoDeTrabajoRepository.deleteById(id);
    }
}
