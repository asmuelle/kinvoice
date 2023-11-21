package com.myapp.service;

import com.myapp.domain.*; // for static metamodels
import com.myapp.domain.PGMeter;
import com.myapp.repository.PGMeterRepository;
import com.myapp.service.criteria.PGMeterCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PGMeter} entities in the database.
 * The main input is a {@link PGMeterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PGMeter} or a {@link Page} of {@link PGMeter} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PGMeterQueryService extends QueryService<PGMeter> {

    private final Logger log = LoggerFactory.getLogger(PGMeterQueryService.class);

    private final PGMeterRepository pGMeterRepository;

    public PGMeterQueryService(PGMeterRepository pGMeterRepository) {
        this.pGMeterRepository = pGMeterRepository;
    }

    /**
     * Return a {@link List} of {@link PGMeter} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PGMeter> findByCriteria(PGMeterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PGMeter> specification = createSpecification(criteria);
        return pGMeterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PGMeter} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PGMeter> findByCriteria(PGMeterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PGMeter> specification = createSpecification(criteria);
        return pGMeterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PGMeterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PGMeter> specification = createSpecification(criteria);
        return pGMeterRepository.count(specification);
    }

    /**
     * Function to convert {@link PGMeterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PGMeter> createSpecification(PGMeterCriteria criteria) {
        Specification<PGMeter> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PGMeter_.id));
            }
            if (criteria.getMeterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeterId(), PGMeter_.meterId));
            }
            if (criteria.getOwnerKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerKey(), PGMeter_.ownerKey));
            }
            if (criteria.getOwnerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerName(), PGMeter_.ownerName));
            }
            if (criteria.getUtility() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUtility(), PGMeter_.utility));
            }
            if (criteria.getNamespace() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNamespace(), PGMeter_.namespace));
            }
            if (criteria.getMeterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMeterName(), PGMeter_.meterName));
            }
            if (criteria.getRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRef(), PGMeter_.ref));
            }
            if (criteria.getSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSite(), PGMeter_.site));
            }
            if (criteria.getLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLat(), PGMeter_.lat));
            }
            if (criteria.getLon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLon(), PGMeter_.lon));
            }
            if (criteria.getWsLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWsLat(), PGMeter_.wsLat));
            }
            if (criteria.getWsLon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWsLon(), PGMeter_.wsLon));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocationId(), PGMeter_.locationId));
            }
            if (criteria.getKm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKm(), PGMeter_.km));
            }
            if (criteria.getPeerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeerName(), PGMeter_.peerName));
            }
            if (criteria.getMarkers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarkers(), PGMeter_.markers));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), PGMeter_.price));
            }
        }
        return specification;
    }
}
