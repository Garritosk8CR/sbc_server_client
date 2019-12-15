package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.PerfilService;
import com.pronix.sbc.domain.Perfil;
import com.pronix.sbc.repository.PerfilRepository;
import com.pronix.sbc.service.dto.PerfilDTO;
import com.pronix.sbc.service.mapper.PerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Perfil}.
 */
@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilServiceImpl.class);

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    public PerfilServiceImpl(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    /**
     * Save a perfil.
     *
     * @param perfilDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PerfilDTO save(PerfilDTO perfilDTO) {
        log.debug("Request to save Perfil : {}", perfilDTO);
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        perfil = perfilRepository.save(perfil);
        return perfilMapper.toDto(perfil);
    }

    /**
     * Get all the perfils.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PerfilDTO> findAll() {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll().stream()
            .map(perfilMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PerfilDTO> findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findById(id)
            .map(perfilMapper::toDto);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.deleteById(id);
    }
}
