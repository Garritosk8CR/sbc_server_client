package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.ConversacionService;
import com.pronix.sbc.domain.Conversacion;
import com.pronix.sbc.repository.ConversacionRepository;
import com.pronix.sbc.service.dto.ConversacionDTO;
import com.pronix.sbc.service.mapper.ConversacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Conversacion}.
 */
@Service
@Transactional
public class ConversacionServiceImpl implements ConversacionService {

    private final Logger log = LoggerFactory.getLogger(ConversacionServiceImpl.class);

    private final ConversacionRepository conversacionRepository;

    private final ConversacionMapper conversacionMapper;

    public ConversacionServiceImpl(ConversacionRepository conversacionRepository, ConversacionMapper conversacionMapper) {
        this.conversacionRepository = conversacionRepository;
        this.conversacionMapper = conversacionMapper;
    }

    /**
     * Save a conversacion.
     *
     * @param conversacionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConversacionDTO save(ConversacionDTO conversacionDTO) {
        log.debug("Request to save Conversacion : {}", conversacionDTO);
        Conversacion conversacion = conversacionMapper.toEntity(conversacionDTO);
        conversacion = conversacionRepository.save(conversacion);
        return conversacionMapper.toDto(conversacion);
    }

    /**
     * Get all the conversacions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConversacionDTO> findAll() {
        log.debug("Request to get all Conversacions");
        return conversacionRepository.findAll().stream()
            .map(conversacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one conversacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConversacionDTO> findOne(Long id) {
        log.debug("Request to get Conversacion : {}", id);
        return conversacionRepository.findById(id)
            .map(conversacionMapper::toDto);
    }

    /**
     * Delete the conversacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conversacion : {}", id);
        conversacionRepository.deleteById(id);
    }
}
