package com.myapp.web.rest;

import com.myapp.domain.PGMeter;
import com.myapp.repository.PGMeterRepository;
import com.myapp.service.PGMeterQueryService;
import com.myapp.service.PGMeterService;
import com.myapp.service.criteria.PGMeterCriteria;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.PGMeter}.
 */
@RestController
@RequestMapping("/api/pg-meters")
public class PGMeterResource {

    private final Logger log = LoggerFactory.getLogger(PGMeterResource.class);

    private static final String ENTITY_NAME = "pGMeter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PGMeterService pGMeterService;

    private final PGMeterRepository pGMeterRepository;

    private final PGMeterQueryService pGMeterQueryService;

    public PGMeterResource(PGMeterService pGMeterService, PGMeterRepository pGMeterRepository, PGMeterQueryService pGMeterQueryService) {
        this.pGMeterService = pGMeterService;
        this.pGMeterRepository = pGMeterRepository;
        this.pGMeterQueryService = pGMeterQueryService;
    }

    /**
     * {@code POST  /pg-meters} : Create a new pGMeter.
     *
     * @param pGMeter the pGMeter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pGMeter, or with status {@code 400 (Bad Request)} if the pGMeter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PGMeter> createPGMeter(@RequestBody PGMeter pGMeter) throws URISyntaxException {
        log.debug("REST request to save PGMeter : {}", pGMeter);
        if (pGMeter.getId() != null) {
            throw new BadRequestAlertException("A new pGMeter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PGMeter result = pGMeterService.save(pGMeter);
        return ResponseEntity
            .created(new URI("/api/pg-meters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-meters/:id} : Updates an existing pGMeter.
     *
     * @param id the id of the pGMeter to save.
     * @param pGMeter the pGMeter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pGMeter,
     * or with status {@code 400 (Bad Request)} if the pGMeter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pGMeter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PGMeter> updatePGMeter(@PathVariable(value = "id", required = false) final Long id, @RequestBody PGMeter pGMeter)
        throws URISyntaxException {
        log.debug("REST request to update PGMeter : {}, {}", id, pGMeter);
        if (pGMeter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pGMeter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pGMeterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PGMeter result = pGMeterService.update(pGMeter);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pGMeter.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pg-meters/:id} : Partial updates given fields of an existing pGMeter, field will ignore if it is null
     *
     * @param id the id of the pGMeter to save.
     * @param pGMeter the pGMeter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pGMeter,
     * or with status {@code 400 (Bad Request)} if the pGMeter is not valid,
     * or with status {@code 404 (Not Found)} if the pGMeter is not found,
     * or with status {@code 500 (Internal Server Error)} if the pGMeter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PGMeter> partialUpdatePGMeter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PGMeter pGMeter
    ) throws URISyntaxException {
        log.debug("REST request to partial update PGMeter partially : {}, {}", id, pGMeter);
        if (pGMeter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pGMeter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pGMeterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PGMeter> result = pGMeterService.partialUpdate(pGMeter);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pGMeter.getId().toString())
        );
    }

    /**
     * {@code GET  /pg-meters} : get all the pGMeters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pGMeters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PGMeter>> getAllPGMeters(PGMeterCriteria criteria) {
        log.debug("REST request to get PGMeters by criteria: {}", criteria);

        List<PGMeter> entityList = pGMeterQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /pg-meters/count} : count all the pGMeters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPGMeters(PGMeterCriteria criteria) {
        log.debug("REST request to count PGMeters by criteria: {}", criteria);
        return ResponseEntity.ok().body(pGMeterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pg-meters/:id} : get the "id" pGMeter.
     *
     * @param id the id of the pGMeter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pGMeter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PGMeter> getPGMeter(@PathVariable Long id) {
        log.debug("REST request to get PGMeter : {}", id);
        Optional<PGMeter> pGMeter = pGMeterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pGMeter);
    }

    /**
     * {@code DELETE  /pg-meters/:id} : delete the "id" pGMeter.
     *
     * @param id the id of the pGMeter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePGMeter(@PathVariable Long id) {
        log.debug("REST request to delete PGMeter : {}", id);
        pGMeterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
