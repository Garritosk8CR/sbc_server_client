package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.PuestoDeTrabajoService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.PuestoDeTrabajoDTO;

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
 * REST controller for managing {@link com.pronix.sbc.domain.PuestoDeTrabajo}.
 */
@RestController
@RequestMapping("/api")
public class PuestoDeTrabajoResource {

    private final Logger log = LoggerFactory.getLogger(PuestoDeTrabajoResource.class);

    private static final String ENTITY_NAME = "puestoDeTrabajo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuestoDeTrabajoService puestoDeTrabajoService;

    public PuestoDeTrabajoResource(PuestoDeTrabajoService puestoDeTrabajoService) {
        this.puestoDeTrabajoService = puestoDeTrabajoService;
    }

    /**
     * {@code POST  /puesto-de-trabajos} : Create a new puestoDeTrabajo.
     *
     * @param puestoDeTrabajoDTO the puestoDeTrabajoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puestoDeTrabajoDTO, or with status {@code 400 (Bad Request)} if the puestoDeTrabajo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puesto-de-trabajos")
    public ResponseEntity<PuestoDeTrabajoDTO> createPuestoDeTrabajo(@Valid @RequestBody PuestoDeTrabajoDTO puestoDeTrabajoDTO) throws URISyntaxException {
        log.debug("REST request to save PuestoDeTrabajo : {}", puestoDeTrabajoDTO);
        if (puestoDeTrabajoDTO.getId() != null) {
            throw new BadRequestAlertException("A new puestoDeTrabajo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuestoDeTrabajoDTO result = puestoDeTrabajoService.save(puestoDeTrabajoDTO);
        return ResponseEntity.created(new URI("/api/puesto-de-trabajos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puesto-de-trabajos} : Updates an existing puestoDeTrabajo.
     *
     * @param puestoDeTrabajoDTO the puestoDeTrabajoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated puestoDeTrabajoDTO,
     * or with status {@code 400 (Bad Request)} if the puestoDeTrabajoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the puestoDeTrabajoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puesto-de-trabajos")
    public ResponseEntity<PuestoDeTrabajoDTO> updatePuestoDeTrabajo(@Valid @RequestBody PuestoDeTrabajoDTO puestoDeTrabajoDTO) throws URISyntaxException {
        log.debug("REST request to update PuestoDeTrabajo : {}", puestoDeTrabajoDTO);
        if (puestoDeTrabajoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuestoDeTrabajoDTO result = puestoDeTrabajoService.save(puestoDeTrabajoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, puestoDeTrabajoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puesto-de-trabajos} : get all the puestoDeTrabajos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puestoDeTrabajos in body.
     */
    @GetMapping("/puesto-de-trabajos")
    public List<PuestoDeTrabajoDTO> getAllPuestoDeTrabajos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PuestoDeTrabajos");
        return puestoDeTrabajoService.findAll();
    }

    /**
     * {@code GET  /puesto-de-trabajos/:id} : get the "id" puestoDeTrabajo.
     *
     * @param id the id of the puestoDeTrabajoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puestoDeTrabajoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puesto-de-trabajos/{id}")
    public ResponseEntity<PuestoDeTrabajoDTO> getPuestoDeTrabajo(@PathVariable Long id) {
        log.debug("REST request to get PuestoDeTrabajo : {}", id);
        Optional<PuestoDeTrabajoDTO> puestoDeTrabajoDTO = puestoDeTrabajoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(puestoDeTrabajoDTO);
    }

    /**
     * {@code DELETE  /puesto-de-trabajos/:id} : delete the "id" puestoDeTrabajo.
     *
     * @param id the id of the puestoDeTrabajoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puesto-de-trabajos/{id}")
    public ResponseEntity<Void> deletePuestoDeTrabajo(@PathVariable Long id) {
        log.debug("REST request to delete PuestoDeTrabajo : {}", id);
        puestoDeTrabajoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
