package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.ConversacionService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.ConversacionDTO;

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
 * REST controller for managing {@link com.pronix.sbc.domain.Conversacion}.
 */
@RestController
@RequestMapping("/api")
public class ConversacionResource {

    private final Logger log = LoggerFactory.getLogger(ConversacionResource.class);

    private static final String ENTITY_NAME = "conversacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConversacionService conversacionService;

    public ConversacionResource(ConversacionService conversacionService) {
        this.conversacionService = conversacionService;
    }

    /**
     * {@code POST  /conversacions} : Create a new conversacion.
     *
     * @param conversacionDTO the conversacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conversacionDTO, or with status {@code 400 (Bad Request)} if the conversacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conversacions")
    public ResponseEntity<ConversacionDTO> createConversacion(@Valid @RequestBody ConversacionDTO conversacionDTO) throws URISyntaxException {
        log.debug("REST request to save Conversacion : {}", conversacionDTO);
        if (conversacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new conversacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConversacionDTO result = conversacionService.save(conversacionDTO);
        return ResponseEntity.created(new URI("/api/conversacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conversacions} : Updates an existing conversacion.
     *
     * @param conversacionDTO the conversacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conversacionDTO,
     * or with status {@code 400 (Bad Request)} if the conversacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conversacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conversacions")
    public ResponseEntity<ConversacionDTO> updateConversacion(@Valid @RequestBody ConversacionDTO conversacionDTO) throws URISyntaxException {
        log.debug("REST request to update Conversacion : {}", conversacionDTO);
        if (conversacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConversacionDTO result = conversacionService.save(conversacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conversacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conversacions} : get all the conversacions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conversacions in body.
     */
    @GetMapping("/conversacions")
    public List<ConversacionDTO> getAllConversacions() {
        log.debug("REST request to get all Conversacions");
        return conversacionService.findAll();
    }

    /**
     * {@code GET  /conversacions/:id} : get the "id" conversacion.
     *
     * @param id the id of the conversacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conversacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conversacions/{id}")
    public ResponseEntity<ConversacionDTO> getConversacion(@PathVariable Long id) {
        log.debug("REST request to get Conversacion : {}", id);
        Optional<ConversacionDTO> conversacionDTO = conversacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conversacionDTO);
    }

    /**
     * {@code DELETE  /conversacions/:id} : delete the "id" conversacion.
     *
     * @param id the id of the conversacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conversacions/{id}")
    public ResponseEntity<Void> deleteConversacion(@PathVariable Long id) {
        log.debug("REST request to delete Conversacion : {}", id);
        conversacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
