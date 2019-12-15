package com.pronix.sbc.service.impl;

import com.pronix.sbc.service.CarreraProfesionalService;
import com.pronix.sbc.domain.CarreraProfesional;
import com.pronix.sbc.repository.CarreraProfesionalRepository;
import com.pronix.sbc.service.dto.CarreraProfesionalDTO;
import com.pronix.sbc.service.mapper.CarreraProfesionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CarreraProfesional}.
 */
@Service
@Transactional
public class CarreraProfesionalServiceImpl implements CarreraProfesionalService {

    private final Logger log = LoggerFactory.getLogger(CarreraProfesionalServiceImpl.class);

    private final CarreraProfesionalRepository carreraProfesionalRepository;

    private final CarreraProfesionalMapper carreraProfesionalMapper;

    public CarreraProfesionalServiceImpl(CarreraProfesionalRepository carreraProfesionalRepository, CarreraProfesionalMapper carreraProfesionalMapper) {
        this.carreraProfesionalRepository = carreraProfesionalRepository;
        this.carreraProfesionalMapper = carreraProfesionalMapper;
    }

    /**
     * Save a carreraProfesional.
     *
     * @param carreraProfesionalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarreraProfesionalDTO save(CarreraProfesionalDTO carreraProfesionalDTO) {
        log.debug("Request to save CarreraProfesional : {}", carreraProfesionalDTO);
        CarreraProfesional carreraProfesional = carreraProfesionalMapper.toEntity(carreraProfesionalDTO);
        carreraProfesional = carreraProfesionalRepository.save(carreraProfesional);
        return carreraProfesionalMapper.toDto(carreraProfesional);
    }

    /**
     * Get all the carreraProfesionals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarreraProfesionalDTO> findAll() {
        log.debug("Request to get all CarreraProfesionals");
        return carreraProfesionalRepository.findAll().stream()
            .map(carreraProfesionalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carreraProfesional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarreraProfesionalDTO> findOne(Long id) {
        log.debug("Request to get CarreraProfesional : {}", id);
        return carreraProfesionalRepository.findById(id)
            .map(carreraProfesionalMapper::toDto);
    }

    /**
     * Delete the carreraProfesional by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarreraProfesional : {}", id);
        carreraProfesionalRepository.deleteById(id);
    }
}
