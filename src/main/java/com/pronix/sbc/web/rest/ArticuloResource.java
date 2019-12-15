package com.pronix.sbc.web.rest;

import com.pronix.sbc.service.ArticuloService;
import com.pronix.sbc.web.rest.errors.BadRequestAlertException;
import com.pronix.sbc.service.dto.ArticuloDTO;

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
 * REST controller for managing {@link com.pronix.sbc.domain.Articulo}.
 */
@RestController
@RequestMapping("/api")
public class ArticuloResource {

    private final Logger log = LoggerFactory.getLogger(ArticuloResource.class);

    private static final String ENTITY_NAME = "articulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticuloService articuloService;

    public ArticuloResource(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    /**
     * {@code POST  /articulos} : Create a new articulo.
     *
     * @param articuloDTO the articuloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articuloDTO, or with status {@code 400 (Bad Request)} if the articulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articulos")
    public ResponseEntity<ArticuloDTO> createArticulo(@Valid @RequestBody ArticuloDTO articuloDTO) throws URISyntaxException {
        log.debug("REST request to save Articulo : {}", articuloDTO);
        if (articuloDTO.getId() != null) {
            throw new BadRequestAlertException("A new articulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticuloDTO result = articuloService.save(articuloDTO);
        return ResponseEntity.created(new URI("/api/articulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articulos} : Updates an existing articulo.
     *
     * @param articuloDTO the articuloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articuloDTO,
     * or with status {@code 400 (Bad Request)} if the articuloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articuloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articulos")
    public ResponseEntity<ArticuloDTO> updateArticulo(@Valid @RequestBody ArticuloDTO articuloDTO) throws URISyntaxException {
        log.debug("REST request to update Articulo : {}", articuloDTO);
        if (articuloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticuloDTO result = articuloService.save(articuloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articuloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articulos} : get all the articulos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articulos in body.
     */
    @GetMapping("/articulos")
    public List<ArticuloDTO> getAllArticulos() {
        log.debug("REST request to get all Articulos");
        return articuloService.findAll();
    }

    /**
     * {@code GET  /articulos/:id} : get the "id" articulo.
     *
     * @param id the id of the articuloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articuloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articulos/{id}")
    public ResponseEntity<ArticuloDTO> getArticulo(@PathVariable Long id) {
        log.debug("REST request to get Articulo : {}", id);
        Optional<ArticuloDTO> articuloDTO = articuloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articuloDTO);
    }

    /**
     * {@code DELETE  /articulos/:id} : delete the "id" articulo.
     *
     * @param id the id of the articuloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articulos/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        log.debug("REST request to delete Articulo : {}", id);
        articuloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
