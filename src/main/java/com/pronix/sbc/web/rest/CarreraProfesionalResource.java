package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.CarreraProfesionalService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.CarreraProfesionalDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pronix.sbc.domain.CarreraProfesional}.
 */
@RestController
@RequestMapping("/api")
public class CarreraProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(CarreraProfesionalResource.class);

    private static final String ENTITY_NAME = "carreraProfesional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarreraProfesionalService carreraProfesionalService;

    public CarreraProfesionalResource(CarreraProfesionalService carreraProfesionalService) {
        this.carreraProfesionalService = carreraProfesionalService;
    }

    /**
     * {@code POST  /carrera-profesionals} : Create a new carreraProfesional.
     *
     * @param carreraProfesionalDTO the carreraProfesionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carreraProfesionalDTO, or with status {@code 400 (Bad Request)} if the carreraProfesional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrera-profesionals")
    public ResponseEntity<CarreraProfesionalDTO> createCarreraProfesional(@Valid @RequestBody CarreraProfesionalDTO carreraProfesionalDTO) throws URISyntaxException {
        log.debug("REST request to save CarreraProfesional : {}", carreraProfesionalDTO);
        if (carreraProfesionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new carreraProfesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarreraProfesionalDTO result = carreraProfesionalService.save(carreraProfesionalDTO);
        return ResponseEntity.created(new URI("/api/carrera-profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrera-profesionals} : Updates an existing carreraProfesional.
     *
     * @param carreraProfesionalDTO the carreraProfesionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carreraProfesionalDTO,
     * or with status {@code 400 (Bad Request)} if the carreraProfesionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carreraProfesionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrera-profesionals")
    public ResponseEntity<CarreraProfesionalDTO> updateCarreraProfesional(@Valid @RequestBody CarreraProfesionalDTO carreraProfesionalDTO) throws URISyntaxException {
        log.debug("REST request to update CarreraProfesional : {}", carreraProfesionalDTO);
        if (carreraProfesionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarreraProfesionalDTO result = carreraProfesionalService.save(carreraProfesionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carreraProfesionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carrera-profesionals} : get all the carreraProfesionals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carreraProfesionals in body.
     */
    @GetMapping("/carrera-profesionals")
    public List<CarreraProfesionalDTO> getAllCarreraProfesionals() {
        log.debug("REST request to get all CarreraProfesionals");
        return carreraProfesionalService.findAll();
    }

    /**
     * {@code GET  /carrera-profesionals/:id} : get the "id" carreraProfesional.
     *
     * @param id the id of the carreraProfesionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carreraProfesionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrera-profesionals/{id}")
    public ResponseEntity<CarreraProfesionalDTO> getCarreraProfesional(@PathVariable Long id) {
        log.debug("REST request to get CarreraProfesional : {}", id);
        Optional<CarreraProfesionalDTO> carreraProfesionalDTO = carreraProfesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carreraProfesionalDTO);
    }

    /**
     * {@code DELETE  /carrera-profesionals/:id} : delete the "id" carreraProfesional.
     *
     * @param id the id of the carreraProfesionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrera-profesionals/{id}")
    public ResponseEntity<Void> deleteCarreraProfesional(@PathVariable Long id) {
        log.debug("REST request to delete CarreraProfesional : {}", id);
        carreraProfesionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
