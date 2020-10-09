package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Entregador;
import com.mycompany.myapp.repository.EntregadorRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Entregador}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntregadorResource {

    private final Logger log = LoggerFactory.getLogger(EntregadorResource.class);

    private static final String ENTITY_NAME = "entregador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntregadorRepository entregadorRepository;

    public EntregadorResource(EntregadorRepository entregadorRepository) {
        this.entregadorRepository = entregadorRepository;
    }

    /**
     * {@code POST  /entregadors} : Create a new entregador.
     *
     * @param entregador the entregador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entregador, or with status {@code 400 (Bad Request)} if the entregador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entregadors")
    public ResponseEntity<Entregador> createEntregador(@Valid @RequestBody Entregador entregador) throws URISyntaxException {
        log.debug("REST request to save Entregador : {}", entregador);
        if (entregador.getId() != null) {
            throw new BadRequestAlertException("A new entregador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entregador result = entregadorRepository.save(entregador);
        return ResponseEntity.created(new URI("/api/entregadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entregadors} : Updates an existing entregador.
     *
     * @param entregador the entregador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entregador,
     * or with status {@code 400 (Bad Request)} if the entregador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entregador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entregadors")
    public ResponseEntity<Entregador> updateEntregador(@Valid @RequestBody Entregador entregador) throws URISyntaxException {
        log.debug("REST request to update Entregador : {}", entregador);
        if (entregador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Entregador result = entregadorRepository.save(entregador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entregador.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entregadors} : get all the entregadors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entregadors in body.
     */
    @GetMapping("/entregadors")
    public List<Entregador> getAllEntregadors() {
        log.debug("REST request to get all Entregadors");
        return entregadorRepository.findAll();
    }

    /**
     * {@code GET  /entregadors/:id} : get the "id" entregador.
     *
     * @param id the id of the entregador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entregador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entregadors/{id}")
    public ResponseEntity<Entregador> getEntregador(@PathVariable Long id) {
        log.debug("REST request to get Entregador : {}", id);
        Optional<Entregador> entregador = entregadorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entregador);
    }

    /**
     * {@code DELETE  /entregadors/:id} : delete the "id" entregador.
     *
     * @param id the id of the entregador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entregadors/{id}")
    public ResponseEntity<Void> deleteEntregador(@PathVariable Long id) {
        log.debug("REST request to delete Entregador : {}", id);
        entregadorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
