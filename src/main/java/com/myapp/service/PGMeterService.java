package com.myapp.service;

import com.myapp.domain.PGMeter;
import com.myapp.repository.PGMeterRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.myapp.domain.PGMeter}.
 */
@Service
@Transactional
public class PGMeterService {

    private final Logger log = LoggerFactory.getLogger(PGMeterService.class);

    private final PGMeterRepository pGMeterRepository;

    public PGMeterService(PGMeterRepository pGMeterRepository) {
        this.pGMeterRepository = pGMeterRepository;
    }

    /**
     * Save a pGMeter.
     *
     * @param pGMeter the entity to save.
     * @return the persisted entity.
     */
    public PGMeter save(PGMeter pGMeter) {
        log.debug("Request to save PGMeter : {}", pGMeter);
        return pGMeterRepository.save(pGMeter);
    }

    /**
     * Update a pGMeter.
     *
     * @param pGMeter the entity to save.
     * @return the persisted entity.
     */
    public PGMeter update(PGMeter pGMeter) {
        log.debug("Request to update PGMeter : {}", pGMeter);
        return pGMeterRepository.save(pGMeter);
    }

    /**
     * Partially update a pGMeter.
     *
     * @param pGMeter the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PGMeter> partialUpdate(PGMeter pGMeter) {
        log.debug("Request to partially update PGMeter : {}", pGMeter);

        return pGMeterRepository
            .findById(pGMeter.getId())
            .map(existingPGMeter -> {
                if (pGMeter.getMeterId() != null) {
                    existingPGMeter.setMeterId(pGMeter.getMeterId());
                }
                if (pGMeter.getOwnerKey() != null) {
                    existingPGMeter.setOwnerKey(pGMeter.getOwnerKey());
                }
                if (pGMeter.getOwnerName() != null) {
                    existingPGMeter.setOwnerName(pGMeter.getOwnerName());
                }
                if (pGMeter.getUtility() != null) {
                    existingPGMeter.setUtility(pGMeter.getUtility());
                }
                if (pGMeter.getNamespace() != null) {
                    existingPGMeter.setNamespace(pGMeter.getNamespace());
                }
                if (pGMeter.getMeterName() != null) {
                    existingPGMeter.setMeterName(pGMeter.getMeterName());
                }
                if (pGMeter.getRef() != null) {
                    existingPGMeter.setRef(pGMeter.getRef());
                }
                if (pGMeter.getSite() != null) {
                    existingPGMeter.setSite(pGMeter.getSite());
                }
                if (pGMeter.getLat() != null) {
                    existingPGMeter.setLat(pGMeter.getLat());
                }
                if (pGMeter.getLon() != null) {
                    existingPGMeter.setLon(pGMeter.getLon());
                }
                if (pGMeter.getWsLat() != null) {
                    existingPGMeter.setWsLat(pGMeter.getWsLat());
                }
                if (pGMeter.getWsLon() != null) {
                    existingPGMeter.setWsLon(pGMeter.getWsLon());
                }
                if (pGMeter.getLocationId() != null) {
                    existingPGMeter.setLocationId(pGMeter.getLocationId());
                }
                if (pGMeter.getKm() != null) {
                    existingPGMeter.setKm(pGMeter.getKm());
                }
                if (pGMeter.getPeerName() != null) {
                    existingPGMeter.setPeerName(pGMeter.getPeerName());
                }
                if (pGMeter.getMarkers() != null) {
                    existingPGMeter.setMarkers(pGMeter.getMarkers());
                }
                if (pGMeter.getPrice() != null) {
                    existingPGMeter.setPrice(pGMeter.getPrice());
                }

                return existingPGMeter;
            })
            .map(pGMeterRepository::save);
    }

    /**
     * Get all the pGMeters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PGMeter> findAll() {
        log.debug("Request to get all PGMeters");
        return pGMeterRepository.findAll();
    }

    /**
     * Get one pGMeter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PGMeter> findOne(Long id) {
        log.debug("Request to get PGMeter : {}", id);
        return pGMeterRepository.findById(id);
    }

    /**
     * Delete the pGMeter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PGMeter : {}", id);
        pGMeterRepository.deleteById(id);
    }
}
