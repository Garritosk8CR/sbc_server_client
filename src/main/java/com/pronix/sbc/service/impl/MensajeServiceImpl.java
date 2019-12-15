package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.MensajeService;
import com.pronix.sbc.domain.Mensaje;
import com.pronix.sbc.repository.MensajeRepository;
import com.pronix.sbc.service.dto.MensajeDTO;
import com.pronix.sbc.service.mapper.MensajeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Mensaje}.
 */
@Service
@Transactional
public class MensajeServiceImpl implements MensajeService {

    private final Logger log = LoggerFactory.getLogger(MensajeServiceImpl.class);

    private final MensajeRepository mensajeRepository;

    private final MensajeMapper mensajeMapper;

    public MensajeServiceImpl(MensajeRepository mensajeRepository, MensajeMapper mensajeMapper) {
        this.mensajeRepository = mensajeRepository;
        this.mensajeMapper = mensajeMapper;
    }

    /**
     * Save a mensaje.
     *
     * @param mensajeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MensajeDTO save(MensajeDTO mensajeDTO) {
        log.debug("Request to save Mensaje : {}", mensajeDTO);
        Mensaje mensaje = mensajeMapper.toEntity(mensajeDTO);
        mensaje = mensajeRepository.save(mensaje);
        return mensajeMapper.toDto(mensaje);
    }

    /**
     * Get all the mensajes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MensajeDTO> findAll() {
        log.debug("Request to get all Mensajes");
        return mensajeRepository.findAll().stream()
            .map(mensajeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mensaje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MensajeDTO> findOne(Long id) {
        log.debug("Request to get Mensaje : {}", id);
        return mensajeRepository.findById(id)
            .map(mensajeMapper::toDto);
    }

    /**
     * Delete the mensaje by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mensaje : {}", id);
        mensajeRepository.deleteById(id);
    }
}
