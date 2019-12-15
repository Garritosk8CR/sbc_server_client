package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.HistoriaUsuarioService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.HistoriaUsuarioDTO;

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
 * REST controller for managing {@link com.pronix.sbc.domain.HistoriaUsuario}.
 */
@RestController
@RequestMapping("/api")
public class HistoriaUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(HistoriaUsuarioResource.class);

    private static final String ENTITY_NAME = "historiaUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriaUsuarioService historiaUsuarioService;

    public HistoriaUsuarioResource(HistoriaUsuarioService historiaUsuarioService) {
        this.historiaUsuarioService = historiaUsuarioService;
    }

    /**
     * {@code POST  /historia-usuarios} : Create a new historiaUsuario.
     *
     * @param historiaUsuarioDTO the historiaUsuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiaUsuarioDTO, or with status {@code 400 (Bad Request)} if the historiaUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historia-usuarios")
    public ResponseEntity<HistoriaUsuarioDTO> createHistoriaUsuario(@Valid @RequestBody HistoriaUsuarioDTO historiaUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to save HistoriaUsuario : {}", historiaUsuarioDTO);
        if (historiaUsuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new historiaUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriaUsuarioDTO result = historiaUsuarioService.save(historiaUsuarioDTO);
        return ResponseEntity.created(new URI("/api/historia-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historia-usuarios} : Updates an existing historiaUsuario.
     *
     * @param historiaUsuarioDTO the historiaUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiaUsuarioDTO,
     * or with status {@code 400 (Bad Request)} if the historiaUsuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiaUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historia-usuarios")
    public ResponseEntity<HistoriaUsuarioDTO> updateHistoriaUsuario(@Valid @RequestBody HistoriaUsuarioDTO historiaUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to update HistoriaUsuario : {}", historiaUsuarioDTO);
        if (historiaUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoriaUsuarioDTO result = historiaUsuarioService.save(historiaUsuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historiaUsuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historia-usuarios} : get all the historiaUsuarios.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiaUsuarios in body.
     */
    @GetMapping("/historia-usuarios")
    public List<HistoriaUsuarioDTO> getAllHistoriaUsuarios() {
        log.debug("REST request to get all HistoriaUsuarios");
        return historiaUsuarioService.findAll();
    }

    /**
     * {@code GET  /historia-usuarios/:id} : get the "id" historiaUsuario.
     *
     * @param id the id of the historiaUsuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiaUsuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historia-usuarios/{id}")
    public ResponseEntity<HistoriaUsuarioDTO> getHistoriaUsuario(@PathVariable Long id) {
        log.debug("REST request to get HistoriaUsuario : {}", id);
        Optional<HistoriaUsuarioDTO> historiaUsuarioDTO = historiaUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historiaUsuarioDTO);
    }

    /**
     * {@code DELETE  /historia-usuarios/:id} : delete the "id" historiaUsuario.
     *
     * @param id the id of the historiaUsuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historia-usuarios/{id}")
    public ResponseEntity<Void> deleteHistoriaUsuario(@PathVariable Long id) {
        log.debug("REST request to delete HistoriaUsuario : {}", id);
        historiaUsuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
