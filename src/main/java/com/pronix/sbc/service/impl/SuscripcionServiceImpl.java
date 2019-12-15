package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.SuscripcionService;
import com.pronix.sbc.domain.Suscripcion;
import com.pronix.sbc.repository.SuscripcionRepository;
import com.pronix.sbc.service.dto.SuscripcionDTO;
import com.pronix.sbc.service.mapper.SuscripcionMapper;
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
 * Service Implementation for managing {@link Suscripcion}.
 */
@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    private final Logger log = LoggerFactory.getLogger(SuscripcionServiceImpl.class);

    private final SuscripcionRepository suscripcionRepository;

    private final SuscripcionMapper suscripcionMapper;

    public SuscripcionServiceImpl(SuscripcionRepository suscripcionRepository, SuscripcionMapper suscripcionMapper) {
        this.suscripcionRepository = suscripcionRepository;
        this.suscripcionMapper = suscripcionMapper;
    }

    /**
     * Save a suscripcion.
     *
     * @param suscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SuscripcionDTO save(SuscripcionDTO suscripcionDTO) {
        log.debug("Request to save Suscripcion : {}", suscripcionDTO);
        Suscripcion suscripcion = suscripcionMapper.toEntity(suscripcionDTO);
        suscripcion = suscripcionRepository.save(suscripcion);
        return suscripcionMapper.toDto(suscripcion);
    }

    /**
     * Get all the suscripcions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionDTO> findAll() {
        log.debug("Request to get all Suscripcions");
        return suscripcionRepository.findAllWithEagerRelationships().stream()
            .map(suscripcionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the suscripcions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SuscripcionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return suscripcionRepository.findAllWithEagerRelationships(pageable).map(suscripcionMapper::toDto);
    }
    

    /**
     * Get one suscripcion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SuscripcionDTO> findOne(Long id) {
        log.debug("Request to get Suscripcion : {}", id);
        return suscripcionRepository.findOneWithEagerRelationships(id)
            .map(suscripcionMapper::toDto);
    }

    /**
     * Delete the suscripcion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suscripcion : {}", id);
        suscripcionRepository.deleteById(id);
    }
}
