package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.MensajeService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.MensajeDTO;

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
 * REST controller for managing {@link com.pronix.sbc.domain.Mensaje}.
 */
@RestController
@RequestMapping("/api")
public class MensajeResource {

    private final Logger log = LoggerFactory.getLogger(MensajeResource.class);

    private static final String ENTITY_NAME = "mensaje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensajeService mensajeService;

    public MensajeResource(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /**
     * {@code POST  /mensajes} : Create a new mensaje.
     *
     * @param mensajeDTO the mensajeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensajeDTO, or with status {@code 400 (Bad Request)} if the mensaje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mensajes")
    public ResponseEntity<MensajeDTO> createMensaje(@Valid @RequestBody MensajeDTO mensajeDTO) throws URISyntaxException {
        log.debug("REST request to save Mensaje : {}", mensajeDTO);
        if (mensajeDTO.getId() != null) {
            throw new BadRequestAlertException("A new mensaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MensajeDTO result = mensajeService.save(mensajeDTO);
        return ResponseEntity.created(new URI("/api/mensajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mensajes} : Updates an existing mensaje.
     *
     * @param mensajeDTO the mensajeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajeDTO,
     * or with status {@code 400 (Bad Request)} if the mensajeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensajeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mensajes")
    public ResponseEntity<MensajeDTO> updateMensaje(@Valid @RequestBody MensajeDTO mensajeDTO) throws URISyntaxException {
        log.debug("REST request to update Mensaje : {}", mensajeDTO);
        if (mensajeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MensajeDTO result = mensajeService.save(mensajeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensajeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mensajes} : get all the mensajes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensajes in body.
     */
    @GetMapping("/mensajes")
    public List<MensajeDTO> getAllMensajes() {
        log.debug("REST request to get all Mensajes");
        return mensajeService.findAll();
    }

    /**
     * {@code GET  /mensajes/:id} : get the "id" mensaje.
     *
     * @param id the id of the mensajeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensajeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mensajes/{id}")
    public ResponseEntity<MensajeDTO> getMensaje(@PathVariable Long id) {
        log.debug("REST request to get Mensaje : {}", id);
        Optional<MensajeDTO> mensajeDTO = mensajeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensajeDTO);
    }

    /**
     * {@code DELETE  /mensajes/:id} : delete the "id" mensaje.
     *
     * @param id the id of the mensajeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        log.debug("REST request to delete Mensaje : {}", id);
        mensajeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
