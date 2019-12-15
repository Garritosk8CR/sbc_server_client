package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.RequisitoService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.RequisitoDTO;

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
 * REST controller for managing {@link com.pronix.sbc.domain.Requisito}.
 */
@RestController
@RequestMapping("/api")
public class RequisitoResource {

    private final Logger log = LoggerFactory.getLogger(RequisitoResource.class);

    private static final String ENTITY_NAME = "requisito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequisitoService requisitoService;

    public RequisitoResource(RequisitoService requisitoService) {
        this.requisitoService = requisitoService;
    }

    /**
     * {@code POST  /requisitos} : Create a new requisito.
     *
     * @param requisitoDTO the requisitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requisitoDTO, or with status {@code 400 (Bad Request)} if the requisito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requisitos")
    public ResponseEntity<RequisitoDTO> createRequisito(@Valid @RequestBody RequisitoDTO requisitoDTO) throws URISyntaxException {
        log.debug("REST request to save Requisito : {}", requisitoDTO);
        if (requisitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitoDTO result = requisitoService.save(requisitoDTO);
        return ResponseEntity.created(new URI("/api/requisitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requisitos} : Updates an existing requisito.
     *
     * @param requisitoDTO the requisitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requisitoDTO,
     * or with status {@code 400 (Bad Request)} if the requisitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requisitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requisitos")
    public ResponseEntity<RequisitoDTO> updateRequisito(@Valid @RequestBody RequisitoDTO requisitoDTO) throws URISyntaxException {
        log.debug("REST request to update Requisito : {}", requisitoDTO);
        if (requisitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitoDTO result = requisitoService.save(requisitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requisitos} : get all the requisitos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requisitos in body.
     */
    @GetMapping("/requisitos")
    public List<RequisitoDTO> getAllRequisitos() {
        log.debug("REST request to get all Requisitos");
        return requisitoService.findAll();
    }

    /**
     * {@code GET  /requisitos/:id} : get the "id" requisito.
     *
     * @param id the id of the requisitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requisitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requisitos/{id}")
    public ResponseEntity<RequisitoDTO> getRequisito(@PathVariable Long id) {
        log.debug("REST request to get Requisito : {}", id);
        Optional<RequisitoDTO> requisitoDTO = requisitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitoDTO);
    }

    /**
     * {@code DELETE  /requisitos/:id} : delete the "id" requisito.
     *
     * @param id the id of the requisitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requisitos/{id}")
    public ResponseEntity<Void> deleteRequisito(@PathVariable Long id) {
        log.debug("REST request to delete Requisito : {}", id);
        requisitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
