package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.RequisitoService;
import com.pronix.sbc.domain.Requisito;
import com.pronix.sbc.repository.RequisitoRepository;
import com.pronix.sbc.service.dto.RequisitoDTO;
import com.pronix.sbc.service.mapper.RequisitoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Requisito}.
 */
@Service
@Transactional
public class RequisitoServiceImpl implements RequisitoService {

    private final Logger log = LoggerFactory.getLogger(RequisitoServiceImpl.class);

    private final RequisitoRepository requisitoRepository;

    private final RequisitoMapper requisitoMapper;

    public RequisitoServiceImpl(RequisitoRepository requisitoRepository, RequisitoMapper requisitoMapper) {
        this.requisitoRepository = requisitoRepository;
        this.requisitoMapper = requisitoMapper;
    }

    /**
     * Save a requisito.
     *
     * @param requisitoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RequisitoDTO save(RequisitoDTO requisitoDTO) {
        log.debug("Request to save Requisito : {}", requisitoDTO);
        Requisito requisito = requisitoMapper.toEntity(requisitoDTO);
        requisito = requisitoRepository.save(requisito);
        return requisitoMapper.toDto(requisito);
    }

    /**
     * Get all the requisitos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RequisitoDTO> findAll() {
        log.debug("Request to get all Requisitos");
        return requisitoRepository.findAll().stream()
            .map(requisitoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one requisito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RequisitoDTO> findOne(Long id) {
        log.debug("Request to get Requisito : {}", id);
        return requisitoRepository.findById(id)
            .map(requisitoMapper::toDto);
    }

    /**
     * Delete the requisito by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requisito : {}", id);
        requisitoRepository.deleteById(id);
    }
}
