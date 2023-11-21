package com.myapp.repository;

import com.myapp.domain.PGMeter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PGMeter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PGMeterRepository extends JpaRepository<PGMeter, Long>, JpaSpecificationExecutor<PGMeter> {}
