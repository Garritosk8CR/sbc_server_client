package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.SuscripcionService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.SuscripcionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pronix.sbc.domain.Suscripcion}.
 */
@RestController
@RequestMapping("/api")
public class SuscripcionResource {

    private final Logger log = LoggerFactory.getLogger(SuscripcionResource.class);

    private static final String ENTITY_NAME = "suscripcion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuscripcionService suscripcionService;

    public SuscripcionResource(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    /**
     * {@code POST  /suscripcions} : Create a new suscripcion.
     *
     * @param suscripcionDTO the suscripcionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suscripcionDTO, or with status {@code 400 (Bad Request)} if the suscripcion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suscripcions")
    public ResponseEntity<SuscripcionDTO> createSuscripcion(@RequestBody SuscripcionDTO suscripcionDTO) throws URISyntaxException {
        log.debug("REST request to save Suscripcion : {}", suscripcionDTO);
        if (suscripcionDTO.getId() != null) {
            throw new BadRequestAlertException("A new suscripcion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuscripcionDTO result = suscripcionService.save(suscripcionDTO);
        return ResponseEntity.created(new URI("/api/suscripcions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suscripcions} : Updates an existing suscripcion.
     *
     * @param suscripcionDTO the suscripcionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suscripcionDTO,
     * or with status {@code 400 (Bad Request)} if the suscripcionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suscripcionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suscripcions")
    public ResponseEntity<SuscripcionDTO> updateSuscripcion(@RequestBody SuscripcionDTO suscripcionDTO) throws URISyntaxException {
        log.debug("REST request to update Suscripcion : {}", suscripcionDTO);
        if (suscripcionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuscripcionDTO result = suscripcionService.save(suscripcionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suscripcionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /suscripcions} : get all the suscripcions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suscripcions in body.
     */
    @GetMapping("/suscripcions")
    public List<SuscripcionDTO> getAllSuscripcions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Suscripcions");
        return suscripcionService.findAll();
    }

    /**
     * {@code GET  /suscripcions/:id} : get the "id" suscripcion.
     *
     * @param id the id of the suscripcionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suscripcionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suscripcions/{id}")
    public ResponseEntity<SuscripcionDTO> getSuscripcion(@PathVariable Long id) {
        log.debug("REST request to get Suscripcion : {}", id);
        Optional<SuscripcionDTO> suscripcionDTO = suscripcionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suscripcionDTO);
    }

    /**
     * {@code DELETE  /suscripcions/:id} : delete the "id" suscripcion.
     *
     * @param id the id of the suscripcionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suscripcions/{id}")
    public ResponseEntity<Void> deleteSuscripcion(@PathVariable Long id) {
        log.debug("REST request to delete Suscripcion : {}", id);
        suscripcionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
