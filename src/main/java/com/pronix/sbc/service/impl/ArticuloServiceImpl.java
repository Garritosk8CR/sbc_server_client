package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.ArticuloService;
import com.pronix.sbc.domain.Articulo;
import com.pronix.sbc.repository.ArticuloRepository;
import com.pronix.sbc.service.dto.ArticuloDTO;
import com.pronix.sbc.service.mapper.ArticuloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Articulo}.
 */
@Service
@Transactional
public class ArticuloServiceImpl implements ArticuloService {

    private final Logger log = LoggerFactory.getLogger(ArticuloServiceImpl.class);

    private final ArticuloRepository articuloRepository;

    private final ArticuloMapper articuloMapper;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository, ArticuloMapper articuloMapper) {
        this.articuloRepository = articuloRepository;
        this.articuloMapper = articuloMapper;
    }

    /**
     * Save a articulo.
     *
     * @param articuloDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ArticuloDTO save(ArticuloDTO articuloDTO) {
        log.debug("Request to save Articulo : {}", articuloDTO);
        Articulo articulo = articuloMapper.toEntity(articuloDTO);
        articulo = articuloRepository.save(articulo);
        return articuloMapper.toDto(articulo);
    }

    /**
     * Get all the articulos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> findAll() {
        log.debug("Request to get all Articulos");
        return articuloRepository.findAll().stream()
            .map(articuloMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one articulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArticuloDTO> findOne(Long id) {
        log.debug("Request to get Articulo : {}", id);
        return articuloRepository.findById(id)
            .map(articuloMapper::toDto);
    }

    /**
     * Delete the articulo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Articulo : {}", id);
        articuloRepository.deleteById(id);
    }
}
