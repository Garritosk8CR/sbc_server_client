package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.HistoriaUsuarioService;
import com.pronix.sbc.domain.HistoriaUsuario;
import com.pronix.sbc.repository.HistoriaUsuarioRepository;
import com.pronix.sbc.service.dto.HistoriaUsuarioDTO;
import com.pronix.sbc.service.mapper.HistoriaUsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link HistoriaUsuario}.
 */
@Service
@Transactional
public class HistoriaUsuarioServiceImpl implements HistoriaUsuarioService {

    private final Logger log = LoggerFactory.getLogger(HistoriaUsuarioServiceImpl.class);

    private final HistoriaUsuarioRepository historiaUsuarioRepository;

    private final HistoriaUsuarioMapper historiaUsuarioMapper;

    public HistoriaUsuarioServiceImpl(HistoriaUsuarioRepository historiaUsuarioRepository, HistoriaUsuarioMapper historiaUsuarioMapper) {
        this.historiaUsuarioRepository = historiaUsuarioRepository;
        this.historiaUsuarioMapper = historiaUsuarioMapper;
    }

    /**
     * Save a historiaUsuario.
     *
     * @param historiaUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HistoriaUsuarioDTO save(HistoriaUsuarioDTO historiaUsuarioDTO) {
        log.debug("Request to save HistoriaUsuario : {}", historiaUsuarioDTO);
        HistoriaUsuario historiaUsuario = historiaUsuarioMapper.toEntity(historiaUsuarioDTO);
        historiaUsuario = historiaUsuarioRepository.save(historiaUsuario);
        return historiaUsuarioMapper.toDto(historiaUsuario);
    }

    /**
     * Get all the historiaUsuarios.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistoriaUsuarioDTO> findAll() {
        log.debug("Request to get all HistoriaUsuarios");
        return historiaUsuarioRepository.findAll().stream()
            .map(historiaUsuarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one historiaUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistoriaUsuarioDTO> findOne(Long id) {
        log.debug("Request to get HistoriaUsuario : {}", id);
        return historiaUsuarioRepository.findById(id)
            .map(historiaUsuarioMapper::toDto);
    }

    /**
     * Delete the historiaUsuario by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoriaUsuario : {}", id);
        historiaUsuarioRepository.deleteById(id);
    }
}
