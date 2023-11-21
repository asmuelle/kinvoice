package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.PGMeter;
import com.myapp.repository.PGMeterRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PGMeterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PGMeterResourceIT {

    private static final Long DEFAULT_METER_ID = 1L;
    private static final Long UPDATED_METER_ID = 2L;
    private static final Long SMALLER_METER_ID = 1L - 1L;

    private static final String DEFAULT_OWNER_KEY = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UTILITY = "AAAAAAAAAA";
    private static final String UPDATED_UTILITY = "BBBBBBBBBB";

    private static final String DEFAULT_NAMESPACE = "AAAAAAAAAA";
    private static final String UPDATED_NAMESPACE = "BBBBBBBBBB";

    private static final String DEFAULT_METER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;
    private static final Float SMALLER_LAT = 1F - 1F;

    private static final Float DEFAULT_LON = 1F;
    private static final Float UPDATED_LON = 2F;
    private static final Float SMALLER_LON = 1F - 1F;

    private static final Float DEFAULT_WS_LAT = 1F;
    private static final Float UPDATED_WS_LAT = 2F;
    private static final Float SMALLER_WS_LAT = 1F - 1F;

    private static final Float DEFAULT_WS_LON = 1F;
    private static final Float UPDATED_WS_LON = 2F;
    private static final Float SMALLER_WS_LON = 1F - 1F;

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;
    private static final Long SMALLER_LOCATION_ID = 1L - 1L;

    private static final Float DEFAULT_KM = 1F;
    private static final Float UPDATED_KM = 2F;
    private static final Float SMALLER_KM = 1F - 1F;

    private static final String DEFAULT_PEER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PEER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MARKERS = "AAAAAAAAAA";
    private static final String UPDATED_MARKERS = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/pg-meters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PGMeterRepository pGMeterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPGMeterMockMvc;

    private PGMeter pGMeter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PGMeter createEntity(EntityManager em) {
        PGMeter pGMeter = new PGMeter()
            .meterId(DEFAULT_METER_ID)
            .ownerKey(DEFAULT_OWNER_KEY)
            .ownerName(DEFAULT_OWNER_NAME)
            .utility(DEFAULT_UTILITY)
            .namespace(DEFAULT_NAMESPACE)
            .meterName(DEFAULT_METER_NAME)
            .ref(DEFAULT_REF)
            .site(DEFAULT_SITE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .wsLat(DEFAULT_WS_LAT)
            .wsLon(DEFAULT_WS_LON)
            .locationId(DEFAULT_LOCATION_ID)
            .km(DEFAULT_KM)
            .peerName(DEFAULT_PEER_NAME)
            .markers(DEFAULT_MARKERS)
            .price(DEFAULT_PRICE);
        return pGMeter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PGMeter createUpdatedEntity(EntityManager em) {
        PGMeter pGMeter = new PGMeter()
            .meterId(UPDATED_METER_ID)
            .ownerKey(UPDATED_OWNER_KEY)
            .ownerName(UPDATED_OWNER_NAME)
            .utility(UPDATED_UTILITY)
            .namespace(UPDATED_NAMESPACE)
            .meterName(UPDATED_METER_NAME)
            .ref(UPDATED_REF)
            .site(UPDATED_SITE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .wsLat(UPDATED_WS_LAT)
            .wsLon(UPDATED_WS_LON)
            .locationId(UPDATED_LOCATION_ID)
            .km(UPDATED_KM)
            .peerName(UPDATED_PEER_NAME)
            .markers(UPDATED_MARKERS)
            .price(UPDATED_PRICE);
        return pGMeter;
    }

    @BeforeEach
    public void initTest() {
        pGMeter = createEntity(em);
    }

    @Test
    @Transactional
    void createPGMeter() throws Exception {
        int databaseSizeBeforeCreate = pGMeterRepository.findAll().size();
        // Create the PGMeter
        restPGMeterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pGMeter)))
            .andExpect(status().isCreated());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeCreate + 1);
        PGMeter testPGMeter = pGMeterList.get(pGMeterList.size() - 1);
        assertThat(testPGMeter.getMeterId()).isEqualTo(DEFAULT_METER_ID);
        assertThat(testPGMeter.getOwnerKey()).isEqualTo(DEFAULT_OWNER_KEY);
        assertThat(testPGMeter.getOwnerName()).isEqualTo(DEFAULT_OWNER_NAME);
        assertThat(testPGMeter.getUtility()).isEqualTo(DEFAULT_UTILITY);
        assertThat(testPGMeter.getNamespace()).isEqualTo(DEFAULT_NAMESPACE);
        assertThat(testPGMeter.getMeterName()).isEqualTo(DEFAULT_METER_NAME);
        assertThat(testPGMeter.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testPGMeter.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testPGMeter.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPGMeter.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testPGMeter.getWsLat()).isEqualTo(DEFAULT_WS_LAT);
        assertThat(testPGMeter.getWsLon()).isEqualTo(DEFAULT_WS_LON);
        assertThat(testPGMeter.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testPGMeter.getKm()).isEqualTo(DEFAULT_KM);
        assertThat(testPGMeter.getPeerName()).isEqualTo(DEFAULT_PEER_NAME);
        assertThat(testPGMeter.getMarkers()).isEqualTo(DEFAULT_MARKERS);
        assertThat(testPGMeter.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createPGMeterWithExistingId() throws Exception {
        // Create the PGMeter with an existing ID
        pGMeter.setId(1L);

        int databaseSizeBeforeCreate = pGMeterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPGMeterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pGMeter)))
            .andExpect(status().isBadRequest());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPGMeters() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList
        restPGMeterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pGMeter.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterId").value(hasItem(DEFAULT_METER_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerKey").value(hasItem(DEFAULT_OWNER_KEY)))
            .andExpect(jsonPath("$.[*].ownerName").value(hasItem(DEFAULT_OWNER_NAME)))
            .andExpect(jsonPath("$.[*].utility").value(hasItem(DEFAULT_UTILITY)))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE)))
            .andExpect(jsonPath("$.[*].meterName").value(hasItem(DEFAULT_METER_NAME)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].wsLat").value(hasItem(DEFAULT_WS_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].wsLon").value(hasItem(DEFAULT_WS_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].peerName").value(hasItem(DEFAULT_PEER_NAME)))
            .andExpect(jsonPath("$.[*].markers").value(hasItem(DEFAULT_MARKERS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getPGMeter() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get the pGMeter
        restPGMeterMockMvc
            .perform(get(ENTITY_API_URL_ID, pGMeter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pGMeter.getId().intValue()))
            .andExpect(jsonPath("$.meterId").value(DEFAULT_METER_ID.intValue()))
            .andExpect(jsonPath("$.ownerKey").value(DEFAULT_OWNER_KEY))
            .andExpect(jsonPath("$.ownerName").value(DEFAULT_OWNER_NAME))
            .andExpect(jsonPath("$.utility").value(DEFAULT_UTILITY))
            .andExpect(jsonPath("$.namespace").value(DEFAULT_NAMESPACE))
            .andExpect(jsonPath("$.meterName").value(DEFAULT_METER_NAME))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.wsLat").value(DEFAULT_WS_LAT.doubleValue()))
            .andExpect(jsonPath("$.wsLon").value(DEFAULT_WS_LON.doubleValue()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.intValue()))
            .andExpect(jsonPath("$.km").value(DEFAULT_KM.doubleValue()))
            .andExpect(jsonPath("$.peerName").value(DEFAULT_PEER_NAME))
            .andExpect(jsonPath("$.markers").value(DEFAULT_MARKERS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getPGMetersByIdFiltering() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        Long id = pGMeter.getId();

        defaultPGMeterShouldBeFound("id.equals=" + id);
        defaultPGMeterShouldNotBeFound("id.notEquals=" + id);

        defaultPGMeterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPGMeterShouldNotBeFound("id.greaterThan=" + id);

        defaultPGMeterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPGMeterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId equals to DEFAULT_METER_ID
        defaultPGMeterShouldBeFound("meterId.equals=" + DEFAULT_METER_ID);

        // Get all the pGMeterList where meterId equals to UPDATED_METER_ID
        defaultPGMeterShouldNotBeFound("meterId.equals=" + UPDATED_METER_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId in DEFAULT_METER_ID or UPDATED_METER_ID
        defaultPGMeterShouldBeFound("meterId.in=" + DEFAULT_METER_ID + "," + UPDATED_METER_ID);

        // Get all the pGMeterList where meterId equals to UPDATED_METER_ID
        defaultPGMeterShouldNotBeFound("meterId.in=" + UPDATED_METER_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId is not null
        defaultPGMeterShouldBeFound("meterId.specified=true");

        // Get all the pGMeterList where meterId is null
        defaultPGMeterShouldNotBeFound("meterId.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId is greater than or equal to DEFAULT_METER_ID
        defaultPGMeterShouldBeFound("meterId.greaterThanOrEqual=" + DEFAULT_METER_ID);

        // Get all the pGMeterList where meterId is greater than or equal to UPDATED_METER_ID
        defaultPGMeterShouldNotBeFound("meterId.greaterThanOrEqual=" + UPDATED_METER_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId is less than or equal to DEFAULT_METER_ID
        defaultPGMeterShouldBeFound("meterId.lessThanOrEqual=" + DEFAULT_METER_ID);

        // Get all the pGMeterList where meterId is less than or equal to SMALLER_METER_ID
        defaultPGMeterShouldNotBeFound("meterId.lessThanOrEqual=" + SMALLER_METER_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId is less than DEFAULT_METER_ID
        defaultPGMeterShouldNotBeFound("meterId.lessThan=" + DEFAULT_METER_ID);

        // Get all the pGMeterList where meterId is less than UPDATED_METER_ID
        defaultPGMeterShouldBeFound("meterId.lessThan=" + UPDATED_METER_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterId is greater than DEFAULT_METER_ID
        defaultPGMeterShouldNotBeFound("meterId.greaterThan=" + DEFAULT_METER_ID);

        // Get all the pGMeterList where meterId is greater than SMALLER_METER_ID
        defaultPGMeterShouldBeFound("meterId.greaterThan=" + SMALLER_METER_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerKey equals to DEFAULT_OWNER_KEY
        defaultPGMeterShouldBeFound("ownerKey.equals=" + DEFAULT_OWNER_KEY);

        // Get all the pGMeterList where ownerKey equals to UPDATED_OWNER_KEY
        defaultPGMeterShouldNotBeFound("ownerKey.equals=" + UPDATED_OWNER_KEY);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerKeyIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerKey in DEFAULT_OWNER_KEY or UPDATED_OWNER_KEY
        defaultPGMeterShouldBeFound("ownerKey.in=" + DEFAULT_OWNER_KEY + "," + UPDATED_OWNER_KEY);

        // Get all the pGMeterList where ownerKey equals to UPDATED_OWNER_KEY
        defaultPGMeterShouldNotBeFound("ownerKey.in=" + UPDATED_OWNER_KEY);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerKey is not null
        defaultPGMeterShouldBeFound("ownerKey.specified=true");

        // Get all the pGMeterList where ownerKey is null
        defaultPGMeterShouldNotBeFound("ownerKey.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerKeyContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerKey contains DEFAULT_OWNER_KEY
        defaultPGMeterShouldBeFound("ownerKey.contains=" + DEFAULT_OWNER_KEY);

        // Get all the pGMeterList where ownerKey contains UPDATED_OWNER_KEY
        defaultPGMeterShouldNotBeFound("ownerKey.contains=" + UPDATED_OWNER_KEY);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerKeyNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerKey does not contain DEFAULT_OWNER_KEY
        defaultPGMeterShouldNotBeFound("ownerKey.doesNotContain=" + DEFAULT_OWNER_KEY);

        // Get all the pGMeterList where ownerKey does not contain UPDATED_OWNER_KEY
        defaultPGMeterShouldBeFound("ownerKey.doesNotContain=" + UPDATED_OWNER_KEY);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerName equals to DEFAULT_OWNER_NAME
        defaultPGMeterShouldBeFound("ownerName.equals=" + DEFAULT_OWNER_NAME);

        // Get all the pGMeterList where ownerName equals to UPDATED_OWNER_NAME
        defaultPGMeterShouldNotBeFound("ownerName.equals=" + UPDATED_OWNER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerNameIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerName in DEFAULT_OWNER_NAME or UPDATED_OWNER_NAME
        defaultPGMeterShouldBeFound("ownerName.in=" + DEFAULT_OWNER_NAME + "," + UPDATED_OWNER_NAME);

        // Get all the pGMeterList where ownerName equals to UPDATED_OWNER_NAME
        defaultPGMeterShouldNotBeFound("ownerName.in=" + UPDATED_OWNER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerName is not null
        defaultPGMeterShouldBeFound("ownerName.specified=true");

        // Get all the pGMeterList where ownerName is null
        defaultPGMeterShouldNotBeFound("ownerName.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerNameContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerName contains DEFAULT_OWNER_NAME
        defaultPGMeterShouldBeFound("ownerName.contains=" + DEFAULT_OWNER_NAME);

        // Get all the pGMeterList where ownerName contains UPDATED_OWNER_NAME
        defaultPGMeterShouldNotBeFound("ownerName.contains=" + UPDATED_OWNER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByOwnerNameNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ownerName does not contain DEFAULT_OWNER_NAME
        defaultPGMeterShouldNotBeFound("ownerName.doesNotContain=" + DEFAULT_OWNER_NAME);

        // Get all the pGMeterList where ownerName does not contain UPDATED_OWNER_NAME
        defaultPGMeterShouldBeFound("ownerName.doesNotContain=" + UPDATED_OWNER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByUtilityIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where utility equals to DEFAULT_UTILITY
        defaultPGMeterShouldBeFound("utility.equals=" + DEFAULT_UTILITY);

        // Get all the pGMeterList where utility equals to UPDATED_UTILITY
        defaultPGMeterShouldNotBeFound("utility.equals=" + UPDATED_UTILITY);
    }

    @Test
    @Transactional
    void getAllPGMetersByUtilityIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where utility in DEFAULT_UTILITY or UPDATED_UTILITY
        defaultPGMeterShouldBeFound("utility.in=" + DEFAULT_UTILITY + "," + UPDATED_UTILITY);

        // Get all the pGMeterList where utility equals to UPDATED_UTILITY
        defaultPGMeterShouldNotBeFound("utility.in=" + UPDATED_UTILITY);
    }

    @Test
    @Transactional
    void getAllPGMetersByUtilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where utility is not null
        defaultPGMeterShouldBeFound("utility.specified=true");

        // Get all the pGMeterList where utility is null
        defaultPGMeterShouldNotBeFound("utility.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByUtilityContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where utility contains DEFAULT_UTILITY
        defaultPGMeterShouldBeFound("utility.contains=" + DEFAULT_UTILITY);

        // Get all the pGMeterList where utility contains UPDATED_UTILITY
        defaultPGMeterShouldNotBeFound("utility.contains=" + UPDATED_UTILITY);
    }

    @Test
    @Transactional
    void getAllPGMetersByUtilityNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where utility does not contain DEFAULT_UTILITY
        defaultPGMeterShouldNotBeFound("utility.doesNotContain=" + DEFAULT_UTILITY);

        // Get all the pGMeterList where utility does not contain UPDATED_UTILITY
        defaultPGMeterShouldBeFound("utility.doesNotContain=" + UPDATED_UTILITY);
    }

    @Test
    @Transactional
    void getAllPGMetersByNamespaceIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where namespace equals to DEFAULT_NAMESPACE
        defaultPGMeterShouldBeFound("namespace.equals=" + DEFAULT_NAMESPACE);

        // Get all the pGMeterList where namespace equals to UPDATED_NAMESPACE
        defaultPGMeterShouldNotBeFound("namespace.equals=" + UPDATED_NAMESPACE);
    }

    @Test
    @Transactional
    void getAllPGMetersByNamespaceIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where namespace in DEFAULT_NAMESPACE or UPDATED_NAMESPACE
        defaultPGMeterShouldBeFound("namespace.in=" + DEFAULT_NAMESPACE + "," + UPDATED_NAMESPACE);

        // Get all the pGMeterList where namespace equals to UPDATED_NAMESPACE
        defaultPGMeterShouldNotBeFound("namespace.in=" + UPDATED_NAMESPACE);
    }

    @Test
    @Transactional
    void getAllPGMetersByNamespaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where namespace is not null
        defaultPGMeterShouldBeFound("namespace.specified=true");

        // Get all the pGMeterList where namespace is null
        defaultPGMeterShouldNotBeFound("namespace.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByNamespaceContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where namespace contains DEFAULT_NAMESPACE
        defaultPGMeterShouldBeFound("namespace.contains=" + DEFAULT_NAMESPACE);

        // Get all the pGMeterList where namespace contains UPDATED_NAMESPACE
        defaultPGMeterShouldNotBeFound("namespace.contains=" + UPDATED_NAMESPACE);
    }

    @Test
    @Transactional
    void getAllPGMetersByNamespaceNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where namespace does not contain DEFAULT_NAMESPACE
        defaultPGMeterShouldNotBeFound("namespace.doesNotContain=" + DEFAULT_NAMESPACE);

        // Get all the pGMeterList where namespace does not contain UPDATED_NAMESPACE
        defaultPGMeterShouldBeFound("namespace.doesNotContain=" + UPDATED_NAMESPACE);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterName equals to DEFAULT_METER_NAME
        defaultPGMeterShouldBeFound("meterName.equals=" + DEFAULT_METER_NAME);

        // Get all the pGMeterList where meterName equals to UPDATED_METER_NAME
        defaultPGMeterShouldNotBeFound("meterName.equals=" + UPDATED_METER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterNameIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterName in DEFAULT_METER_NAME or UPDATED_METER_NAME
        defaultPGMeterShouldBeFound("meterName.in=" + DEFAULT_METER_NAME + "," + UPDATED_METER_NAME);

        // Get all the pGMeterList where meterName equals to UPDATED_METER_NAME
        defaultPGMeterShouldNotBeFound("meterName.in=" + UPDATED_METER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterName is not null
        defaultPGMeterShouldBeFound("meterName.specified=true");

        // Get all the pGMeterList where meterName is null
        defaultPGMeterShouldNotBeFound("meterName.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterNameContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterName contains DEFAULT_METER_NAME
        defaultPGMeterShouldBeFound("meterName.contains=" + DEFAULT_METER_NAME);

        // Get all the pGMeterList where meterName contains UPDATED_METER_NAME
        defaultPGMeterShouldNotBeFound("meterName.contains=" + UPDATED_METER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByMeterNameNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where meterName does not contain DEFAULT_METER_NAME
        defaultPGMeterShouldNotBeFound("meterName.doesNotContain=" + DEFAULT_METER_NAME);

        // Get all the pGMeterList where meterName does not contain UPDATED_METER_NAME
        defaultPGMeterShouldBeFound("meterName.doesNotContain=" + UPDATED_METER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByRefIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ref equals to DEFAULT_REF
        defaultPGMeterShouldBeFound("ref.equals=" + DEFAULT_REF);

        // Get all the pGMeterList where ref equals to UPDATED_REF
        defaultPGMeterShouldNotBeFound("ref.equals=" + UPDATED_REF);
    }

    @Test
    @Transactional
    void getAllPGMetersByRefIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ref in DEFAULT_REF or UPDATED_REF
        defaultPGMeterShouldBeFound("ref.in=" + DEFAULT_REF + "," + UPDATED_REF);

        // Get all the pGMeterList where ref equals to UPDATED_REF
        defaultPGMeterShouldNotBeFound("ref.in=" + UPDATED_REF);
    }

    @Test
    @Transactional
    void getAllPGMetersByRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ref is not null
        defaultPGMeterShouldBeFound("ref.specified=true");

        // Get all the pGMeterList where ref is null
        defaultPGMeterShouldNotBeFound("ref.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByRefContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ref contains DEFAULT_REF
        defaultPGMeterShouldBeFound("ref.contains=" + DEFAULT_REF);

        // Get all the pGMeterList where ref contains UPDATED_REF
        defaultPGMeterShouldNotBeFound("ref.contains=" + UPDATED_REF);
    }

    @Test
    @Transactional
    void getAllPGMetersByRefNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where ref does not contain DEFAULT_REF
        defaultPGMeterShouldNotBeFound("ref.doesNotContain=" + DEFAULT_REF);

        // Get all the pGMeterList where ref does not contain UPDATED_REF
        defaultPGMeterShouldBeFound("ref.doesNotContain=" + UPDATED_REF);
    }

    @Test
    @Transactional
    void getAllPGMetersBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where site equals to DEFAULT_SITE
        defaultPGMeterShouldBeFound("site.equals=" + DEFAULT_SITE);

        // Get all the pGMeterList where site equals to UPDATED_SITE
        defaultPGMeterShouldNotBeFound("site.equals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllPGMetersBySiteIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where site in DEFAULT_SITE or UPDATED_SITE
        defaultPGMeterShouldBeFound("site.in=" + DEFAULT_SITE + "," + UPDATED_SITE);

        // Get all the pGMeterList where site equals to UPDATED_SITE
        defaultPGMeterShouldNotBeFound("site.in=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllPGMetersBySiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where site is not null
        defaultPGMeterShouldBeFound("site.specified=true");

        // Get all the pGMeterList where site is null
        defaultPGMeterShouldNotBeFound("site.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersBySiteContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where site contains DEFAULT_SITE
        defaultPGMeterShouldBeFound("site.contains=" + DEFAULT_SITE);

        // Get all the pGMeterList where site contains UPDATED_SITE
        defaultPGMeterShouldNotBeFound("site.contains=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllPGMetersBySiteNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where site does not contain DEFAULT_SITE
        defaultPGMeterShouldNotBeFound("site.doesNotContain=" + DEFAULT_SITE);

        // Get all the pGMeterList where site does not contain UPDATED_SITE
        defaultPGMeterShouldBeFound("site.doesNotContain=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat equals to DEFAULT_LAT
        defaultPGMeterShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the pGMeterList where lat equals to UPDATED_LAT
        defaultPGMeterShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultPGMeterShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the pGMeterList where lat equals to UPDATED_LAT
        defaultPGMeterShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat is not null
        defaultPGMeterShouldBeFound("lat.specified=true");

        // Get all the pGMeterList where lat is null
        defaultPGMeterShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat is greater than or equal to DEFAULT_LAT
        defaultPGMeterShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the pGMeterList where lat is greater than or equal to UPDATED_LAT
        defaultPGMeterShouldNotBeFound("lat.greaterThanOrEqual=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat is less than or equal to DEFAULT_LAT
        defaultPGMeterShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the pGMeterList where lat is less than or equal to SMALLER_LAT
        defaultPGMeterShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat is less than DEFAULT_LAT
        defaultPGMeterShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the pGMeterList where lat is less than UPDATED_LAT
        defaultPGMeterShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lat is greater than DEFAULT_LAT
        defaultPGMeterShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the pGMeterList where lat is greater than SMALLER_LAT
        defaultPGMeterShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon equals to DEFAULT_LON
        defaultPGMeterShouldBeFound("lon.equals=" + DEFAULT_LON);

        // Get all the pGMeterList where lon equals to UPDATED_LON
        defaultPGMeterShouldNotBeFound("lon.equals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon in DEFAULT_LON or UPDATED_LON
        defaultPGMeterShouldBeFound("lon.in=" + DEFAULT_LON + "," + UPDATED_LON);

        // Get all the pGMeterList where lon equals to UPDATED_LON
        defaultPGMeterShouldNotBeFound("lon.in=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon is not null
        defaultPGMeterShouldBeFound("lon.specified=true");

        // Get all the pGMeterList where lon is null
        defaultPGMeterShouldNotBeFound("lon.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon is greater than or equal to DEFAULT_LON
        defaultPGMeterShouldBeFound("lon.greaterThanOrEqual=" + DEFAULT_LON);

        // Get all the pGMeterList where lon is greater than or equal to UPDATED_LON
        defaultPGMeterShouldNotBeFound("lon.greaterThanOrEqual=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon is less than or equal to DEFAULT_LON
        defaultPGMeterShouldBeFound("lon.lessThanOrEqual=" + DEFAULT_LON);

        // Get all the pGMeterList where lon is less than or equal to SMALLER_LON
        defaultPGMeterShouldNotBeFound("lon.lessThanOrEqual=" + SMALLER_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon is less than DEFAULT_LON
        defaultPGMeterShouldNotBeFound("lon.lessThan=" + DEFAULT_LON);

        // Get all the pGMeterList where lon is less than UPDATED_LON
        defaultPGMeterShouldBeFound("lon.lessThan=" + UPDATED_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByLonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where lon is greater than DEFAULT_LON
        defaultPGMeterShouldNotBeFound("lon.greaterThan=" + DEFAULT_LON);

        // Get all the pGMeterList where lon is greater than SMALLER_LON
        defaultPGMeterShouldBeFound("lon.greaterThan=" + SMALLER_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat equals to DEFAULT_WS_LAT
        defaultPGMeterShouldBeFound("wsLat.equals=" + DEFAULT_WS_LAT);

        // Get all the pGMeterList where wsLat equals to UPDATED_WS_LAT
        defaultPGMeterShouldNotBeFound("wsLat.equals=" + UPDATED_WS_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat in DEFAULT_WS_LAT or UPDATED_WS_LAT
        defaultPGMeterShouldBeFound("wsLat.in=" + DEFAULT_WS_LAT + "," + UPDATED_WS_LAT);

        // Get all the pGMeterList where wsLat equals to UPDATED_WS_LAT
        defaultPGMeterShouldNotBeFound("wsLat.in=" + UPDATED_WS_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat is not null
        defaultPGMeterShouldBeFound("wsLat.specified=true");

        // Get all the pGMeterList where wsLat is null
        defaultPGMeterShouldNotBeFound("wsLat.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat is greater than or equal to DEFAULT_WS_LAT
        defaultPGMeterShouldBeFound("wsLat.greaterThanOrEqual=" + DEFAULT_WS_LAT);

        // Get all the pGMeterList where wsLat is greater than or equal to UPDATED_WS_LAT
        defaultPGMeterShouldNotBeFound("wsLat.greaterThanOrEqual=" + UPDATED_WS_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat is less than or equal to DEFAULT_WS_LAT
        defaultPGMeterShouldBeFound("wsLat.lessThanOrEqual=" + DEFAULT_WS_LAT);

        // Get all the pGMeterList where wsLat is less than or equal to SMALLER_WS_LAT
        defaultPGMeterShouldNotBeFound("wsLat.lessThanOrEqual=" + SMALLER_WS_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat is less than DEFAULT_WS_LAT
        defaultPGMeterShouldNotBeFound("wsLat.lessThan=" + DEFAULT_WS_LAT);

        // Get all the pGMeterList where wsLat is less than UPDATED_WS_LAT
        defaultPGMeterShouldBeFound("wsLat.lessThan=" + UPDATED_WS_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLat is greater than DEFAULT_WS_LAT
        defaultPGMeterShouldNotBeFound("wsLat.greaterThan=" + DEFAULT_WS_LAT);

        // Get all the pGMeterList where wsLat is greater than SMALLER_WS_LAT
        defaultPGMeterShouldBeFound("wsLat.greaterThan=" + SMALLER_WS_LAT);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon equals to DEFAULT_WS_LON
        defaultPGMeterShouldBeFound("wsLon.equals=" + DEFAULT_WS_LON);

        // Get all the pGMeterList where wsLon equals to UPDATED_WS_LON
        defaultPGMeterShouldNotBeFound("wsLon.equals=" + UPDATED_WS_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon in DEFAULT_WS_LON or UPDATED_WS_LON
        defaultPGMeterShouldBeFound("wsLon.in=" + DEFAULT_WS_LON + "," + UPDATED_WS_LON);

        // Get all the pGMeterList where wsLon equals to UPDATED_WS_LON
        defaultPGMeterShouldNotBeFound("wsLon.in=" + UPDATED_WS_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon is not null
        defaultPGMeterShouldBeFound("wsLon.specified=true");

        // Get all the pGMeterList where wsLon is null
        defaultPGMeterShouldNotBeFound("wsLon.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon is greater than or equal to DEFAULT_WS_LON
        defaultPGMeterShouldBeFound("wsLon.greaterThanOrEqual=" + DEFAULT_WS_LON);

        // Get all the pGMeterList where wsLon is greater than or equal to UPDATED_WS_LON
        defaultPGMeterShouldNotBeFound("wsLon.greaterThanOrEqual=" + UPDATED_WS_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon is less than or equal to DEFAULT_WS_LON
        defaultPGMeterShouldBeFound("wsLon.lessThanOrEqual=" + DEFAULT_WS_LON);

        // Get all the pGMeterList where wsLon is less than or equal to SMALLER_WS_LON
        defaultPGMeterShouldNotBeFound("wsLon.lessThanOrEqual=" + SMALLER_WS_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon is less than DEFAULT_WS_LON
        defaultPGMeterShouldNotBeFound("wsLon.lessThan=" + DEFAULT_WS_LON);

        // Get all the pGMeterList where wsLon is less than UPDATED_WS_LON
        defaultPGMeterShouldBeFound("wsLon.lessThan=" + UPDATED_WS_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByWsLonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where wsLon is greater than DEFAULT_WS_LON
        defaultPGMeterShouldNotBeFound("wsLon.greaterThan=" + DEFAULT_WS_LON);

        // Get all the pGMeterList where wsLon is greater than SMALLER_WS_LON
        defaultPGMeterShouldBeFound("wsLon.greaterThan=" + SMALLER_WS_LON);
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId equals to DEFAULT_LOCATION_ID
        defaultPGMeterShouldBeFound("locationId.equals=" + DEFAULT_LOCATION_ID);

        // Get all the pGMeterList where locationId equals to UPDATED_LOCATION_ID
        defaultPGMeterShouldNotBeFound("locationId.equals=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId in DEFAULT_LOCATION_ID or UPDATED_LOCATION_ID
        defaultPGMeterShouldBeFound("locationId.in=" + DEFAULT_LOCATION_ID + "," + UPDATED_LOCATION_ID);

        // Get all the pGMeterList where locationId equals to UPDATED_LOCATION_ID
        defaultPGMeterShouldNotBeFound("locationId.in=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId is not null
        defaultPGMeterShouldBeFound("locationId.specified=true");

        // Get all the pGMeterList where locationId is null
        defaultPGMeterShouldNotBeFound("locationId.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId is greater than or equal to DEFAULT_LOCATION_ID
        defaultPGMeterShouldBeFound("locationId.greaterThanOrEqual=" + DEFAULT_LOCATION_ID);

        // Get all the pGMeterList where locationId is greater than or equal to UPDATED_LOCATION_ID
        defaultPGMeterShouldNotBeFound("locationId.greaterThanOrEqual=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId is less than or equal to DEFAULT_LOCATION_ID
        defaultPGMeterShouldBeFound("locationId.lessThanOrEqual=" + DEFAULT_LOCATION_ID);

        // Get all the pGMeterList where locationId is less than or equal to SMALLER_LOCATION_ID
        defaultPGMeterShouldNotBeFound("locationId.lessThanOrEqual=" + SMALLER_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId is less than DEFAULT_LOCATION_ID
        defaultPGMeterShouldNotBeFound("locationId.lessThan=" + DEFAULT_LOCATION_ID);

        // Get all the pGMeterList where locationId is less than UPDATED_LOCATION_ID
        defaultPGMeterShouldBeFound("locationId.lessThan=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByLocationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where locationId is greater than DEFAULT_LOCATION_ID
        defaultPGMeterShouldNotBeFound("locationId.greaterThan=" + DEFAULT_LOCATION_ID);

        // Get all the pGMeterList where locationId is greater than SMALLER_LOCATION_ID
        defaultPGMeterShouldBeFound("locationId.greaterThan=" + SMALLER_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km equals to DEFAULT_KM
        defaultPGMeterShouldBeFound("km.equals=" + DEFAULT_KM);

        // Get all the pGMeterList where km equals to UPDATED_KM
        defaultPGMeterShouldNotBeFound("km.equals=" + UPDATED_KM);
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km in DEFAULT_KM or UPDATED_KM
        defaultPGMeterShouldBeFound("km.in=" + DEFAULT_KM + "," + UPDATED_KM);

        // Get all the pGMeterList where km equals to UPDATED_KM
        defaultPGMeterShouldNotBeFound("km.in=" + UPDATED_KM);
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km is not null
        defaultPGMeterShouldBeFound("km.specified=true");

        // Get all the pGMeterList where km is null
        defaultPGMeterShouldNotBeFound("km.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km is greater than or equal to DEFAULT_KM
        defaultPGMeterShouldBeFound("km.greaterThanOrEqual=" + DEFAULT_KM);

        // Get all the pGMeterList where km is greater than or equal to UPDATED_KM
        defaultPGMeterShouldNotBeFound("km.greaterThanOrEqual=" + UPDATED_KM);
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km is less than or equal to DEFAULT_KM
        defaultPGMeterShouldBeFound("km.lessThanOrEqual=" + DEFAULT_KM);

        // Get all the pGMeterList where km is less than or equal to SMALLER_KM
        defaultPGMeterShouldNotBeFound("km.lessThanOrEqual=" + SMALLER_KM);
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km is less than DEFAULT_KM
        defaultPGMeterShouldNotBeFound("km.lessThan=" + DEFAULT_KM);

        // Get all the pGMeterList where km is less than UPDATED_KM
        defaultPGMeterShouldBeFound("km.lessThan=" + UPDATED_KM);
    }

    @Test
    @Transactional
    void getAllPGMetersByKmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where km is greater than DEFAULT_KM
        defaultPGMeterShouldNotBeFound("km.greaterThan=" + DEFAULT_KM);

        // Get all the pGMeterList where km is greater than SMALLER_KM
        defaultPGMeterShouldBeFound("km.greaterThan=" + SMALLER_KM);
    }

    @Test
    @Transactional
    void getAllPGMetersByPeerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where peerName equals to DEFAULT_PEER_NAME
        defaultPGMeterShouldBeFound("peerName.equals=" + DEFAULT_PEER_NAME);

        // Get all the pGMeterList where peerName equals to UPDATED_PEER_NAME
        defaultPGMeterShouldNotBeFound("peerName.equals=" + UPDATED_PEER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByPeerNameIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where peerName in DEFAULT_PEER_NAME or UPDATED_PEER_NAME
        defaultPGMeterShouldBeFound("peerName.in=" + DEFAULT_PEER_NAME + "," + UPDATED_PEER_NAME);

        // Get all the pGMeterList where peerName equals to UPDATED_PEER_NAME
        defaultPGMeterShouldNotBeFound("peerName.in=" + UPDATED_PEER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByPeerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where peerName is not null
        defaultPGMeterShouldBeFound("peerName.specified=true");

        // Get all the pGMeterList where peerName is null
        defaultPGMeterShouldNotBeFound("peerName.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByPeerNameContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where peerName contains DEFAULT_PEER_NAME
        defaultPGMeterShouldBeFound("peerName.contains=" + DEFAULT_PEER_NAME);

        // Get all the pGMeterList where peerName contains UPDATED_PEER_NAME
        defaultPGMeterShouldNotBeFound("peerName.contains=" + UPDATED_PEER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByPeerNameNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where peerName does not contain DEFAULT_PEER_NAME
        defaultPGMeterShouldNotBeFound("peerName.doesNotContain=" + DEFAULT_PEER_NAME);

        // Get all the pGMeterList where peerName does not contain UPDATED_PEER_NAME
        defaultPGMeterShouldBeFound("peerName.doesNotContain=" + UPDATED_PEER_NAME);
    }

    @Test
    @Transactional
    void getAllPGMetersByMarkersIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where markers equals to DEFAULT_MARKERS
        defaultPGMeterShouldBeFound("markers.equals=" + DEFAULT_MARKERS);

        // Get all the pGMeterList where markers equals to UPDATED_MARKERS
        defaultPGMeterShouldNotBeFound("markers.equals=" + UPDATED_MARKERS);
    }

    @Test
    @Transactional
    void getAllPGMetersByMarkersIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where markers in DEFAULT_MARKERS or UPDATED_MARKERS
        defaultPGMeterShouldBeFound("markers.in=" + DEFAULT_MARKERS + "," + UPDATED_MARKERS);

        // Get all the pGMeterList where markers equals to UPDATED_MARKERS
        defaultPGMeterShouldNotBeFound("markers.in=" + UPDATED_MARKERS);
    }

    @Test
    @Transactional
    void getAllPGMetersByMarkersIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where markers is not null
        defaultPGMeterShouldBeFound("markers.specified=true");

        // Get all the pGMeterList where markers is null
        defaultPGMeterShouldNotBeFound("markers.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByMarkersContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where markers contains DEFAULT_MARKERS
        defaultPGMeterShouldBeFound("markers.contains=" + DEFAULT_MARKERS);

        // Get all the pGMeterList where markers contains UPDATED_MARKERS
        defaultPGMeterShouldNotBeFound("markers.contains=" + UPDATED_MARKERS);
    }

    @Test
    @Transactional
    void getAllPGMetersByMarkersNotContainsSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where markers does not contain DEFAULT_MARKERS
        defaultPGMeterShouldNotBeFound("markers.doesNotContain=" + DEFAULT_MARKERS);

        // Get all the pGMeterList where markers does not contain UPDATED_MARKERS
        defaultPGMeterShouldBeFound("markers.doesNotContain=" + UPDATED_MARKERS);
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price equals to DEFAULT_PRICE
        defaultPGMeterShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the pGMeterList where price equals to UPDATED_PRICE
        defaultPGMeterShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultPGMeterShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the pGMeterList where price equals to UPDATED_PRICE
        defaultPGMeterShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price is not null
        defaultPGMeterShouldBeFound("price.specified=true");

        // Get all the pGMeterList where price is null
        defaultPGMeterShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price is greater than or equal to DEFAULT_PRICE
        defaultPGMeterShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the pGMeterList where price is greater than or equal to UPDATED_PRICE
        defaultPGMeterShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price is less than or equal to DEFAULT_PRICE
        defaultPGMeterShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the pGMeterList where price is less than or equal to SMALLER_PRICE
        defaultPGMeterShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price is less than DEFAULT_PRICE
        defaultPGMeterShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the pGMeterList where price is less than UPDATED_PRICE
        defaultPGMeterShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPGMetersByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        // Get all the pGMeterList where price is greater than DEFAULT_PRICE
        defaultPGMeterShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the pGMeterList where price is greater than SMALLER_PRICE
        defaultPGMeterShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPGMeterShouldBeFound(String filter) throws Exception {
        restPGMeterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pGMeter.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterId").value(hasItem(DEFAULT_METER_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownerKey").value(hasItem(DEFAULT_OWNER_KEY)))
            .andExpect(jsonPath("$.[*].ownerName").value(hasItem(DEFAULT_OWNER_NAME)))
            .andExpect(jsonPath("$.[*].utility").value(hasItem(DEFAULT_UTILITY)))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE)))
            .andExpect(jsonPath("$.[*].meterName").value(hasItem(DEFAULT_METER_NAME)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].wsLat").value(hasItem(DEFAULT_WS_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].wsLon").value(hasItem(DEFAULT_WS_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].peerName").value(hasItem(DEFAULT_PEER_NAME)))
            .andExpect(jsonPath("$.[*].markers").value(hasItem(DEFAULT_MARKERS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restPGMeterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPGMeterShouldNotBeFound(String filter) throws Exception {
        restPGMeterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPGMeterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPGMeter() throws Exception {
        // Get the pGMeter
        restPGMeterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPGMeter() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();

        // Update the pGMeter
        PGMeter updatedPGMeter = pGMeterRepository.findById(pGMeter.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPGMeter are not directly saved in db
        em.detach(updatedPGMeter);
        updatedPGMeter
            .meterId(UPDATED_METER_ID)
            .ownerKey(UPDATED_OWNER_KEY)
            .ownerName(UPDATED_OWNER_NAME)
            .utility(UPDATED_UTILITY)
            .namespace(UPDATED_NAMESPACE)
            .meterName(UPDATED_METER_NAME)
            .ref(UPDATED_REF)
            .site(UPDATED_SITE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .wsLat(UPDATED_WS_LAT)
            .wsLon(UPDATED_WS_LON)
            .locationId(UPDATED_LOCATION_ID)
            .km(UPDATED_KM)
            .peerName(UPDATED_PEER_NAME)
            .markers(UPDATED_MARKERS)
            .price(UPDATED_PRICE);

        restPGMeterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPGMeter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPGMeter))
            )
            .andExpect(status().isOk());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
        PGMeter testPGMeter = pGMeterList.get(pGMeterList.size() - 1);
        assertThat(testPGMeter.getMeterId()).isEqualTo(UPDATED_METER_ID);
        assertThat(testPGMeter.getOwnerKey()).isEqualTo(UPDATED_OWNER_KEY);
        assertThat(testPGMeter.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testPGMeter.getUtility()).isEqualTo(UPDATED_UTILITY);
        assertThat(testPGMeter.getNamespace()).isEqualTo(UPDATED_NAMESPACE);
        assertThat(testPGMeter.getMeterName()).isEqualTo(UPDATED_METER_NAME);
        assertThat(testPGMeter.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testPGMeter.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testPGMeter.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPGMeter.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testPGMeter.getWsLat()).isEqualTo(UPDATED_WS_LAT);
        assertThat(testPGMeter.getWsLon()).isEqualTo(UPDATED_WS_LON);
        assertThat(testPGMeter.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testPGMeter.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testPGMeter.getPeerName()).isEqualTo(UPDATED_PEER_NAME);
        assertThat(testPGMeter.getMarkers()).isEqualTo(UPDATED_MARKERS);
        assertThat(testPGMeter.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingPGMeter() throws Exception {
        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();
        pGMeter.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPGMeterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pGMeter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pGMeter))
            )
            .andExpect(status().isBadRequest());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPGMeter() throws Exception {
        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();
        pGMeter.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPGMeterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pGMeter))
            )
            .andExpect(status().isBadRequest());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPGMeter() throws Exception {
        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();
        pGMeter.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPGMeterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pGMeter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePGMeterWithPatch() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();

        // Update the pGMeter using partial update
        PGMeter partialUpdatedPGMeter = new PGMeter();
        partialUpdatedPGMeter.setId(pGMeter.getId());

        partialUpdatedPGMeter
            .meterId(UPDATED_METER_ID)
            .ownerName(UPDATED_OWNER_NAME)
            .utility(UPDATED_UTILITY)
            .meterName(UPDATED_METER_NAME)
            .km(UPDATED_KM);

        restPGMeterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPGMeter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPGMeter))
            )
            .andExpect(status().isOk());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
        PGMeter testPGMeter = pGMeterList.get(pGMeterList.size() - 1);
        assertThat(testPGMeter.getMeterId()).isEqualTo(UPDATED_METER_ID);
        assertThat(testPGMeter.getOwnerKey()).isEqualTo(DEFAULT_OWNER_KEY);
        assertThat(testPGMeter.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testPGMeter.getUtility()).isEqualTo(UPDATED_UTILITY);
        assertThat(testPGMeter.getNamespace()).isEqualTo(DEFAULT_NAMESPACE);
        assertThat(testPGMeter.getMeterName()).isEqualTo(UPDATED_METER_NAME);
        assertThat(testPGMeter.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testPGMeter.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testPGMeter.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPGMeter.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testPGMeter.getWsLat()).isEqualTo(DEFAULT_WS_LAT);
        assertThat(testPGMeter.getWsLon()).isEqualTo(DEFAULT_WS_LON);
        assertThat(testPGMeter.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testPGMeter.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testPGMeter.getPeerName()).isEqualTo(DEFAULT_PEER_NAME);
        assertThat(testPGMeter.getMarkers()).isEqualTo(DEFAULT_MARKERS);
        assertThat(testPGMeter.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdatePGMeterWithPatch() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();

        // Update the pGMeter using partial update
        PGMeter partialUpdatedPGMeter = new PGMeter();
        partialUpdatedPGMeter.setId(pGMeter.getId());

        partialUpdatedPGMeter
            .meterId(UPDATED_METER_ID)
            .ownerKey(UPDATED_OWNER_KEY)
            .ownerName(UPDATED_OWNER_NAME)
            .utility(UPDATED_UTILITY)
            .namespace(UPDATED_NAMESPACE)
            .meterName(UPDATED_METER_NAME)
            .ref(UPDATED_REF)
            .site(UPDATED_SITE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .wsLat(UPDATED_WS_LAT)
            .wsLon(UPDATED_WS_LON)
            .locationId(UPDATED_LOCATION_ID)
            .km(UPDATED_KM)
            .peerName(UPDATED_PEER_NAME)
            .markers(UPDATED_MARKERS)
            .price(UPDATED_PRICE);

        restPGMeterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPGMeter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPGMeter))
            )
            .andExpect(status().isOk());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
        PGMeter testPGMeter = pGMeterList.get(pGMeterList.size() - 1);
        assertThat(testPGMeter.getMeterId()).isEqualTo(UPDATED_METER_ID);
        assertThat(testPGMeter.getOwnerKey()).isEqualTo(UPDATED_OWNER_KEY);
        assertThat(testPGMeter.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testPGMeter.getUtility()).isEqualTo(UPDATED_UTILITY);
        assertThat(testPGMeter.getNamespace()).isEqualTo(UPDATED_NAMESPACE);
        assertThat(testPGMeter.getMeterName()).isEqualTo(UPDATED_METER_NAME);
        assertThat(testPGMeter.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testPGMeter.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testPGMeter.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPGMeter.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testPGMeter.getWsLat()).isEqualTo(UPDATED_WS_LAT);
        assertThat(testPGMeter.getWsLon()).isEqualTo(UPDATED_WS_LON);
        assertThat(testPGMeter.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testPGMeter.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testPGMeter.getPeerName()).isEqualTo(UPDATED_PEER_NAME);
        assertThat(testPGMeter.getMarkers()).isEqualTo(UPDATED_MARKERS);
        assertThat(testPGMeter.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingPGMeter() throws Exception {
        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();
        pGMeter.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPGMeterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pGMeter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pGMeter))
            )
            .andExpect(status().isBadRequest());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPGMeter() throws Exception {
        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();
        pGMeter.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPGMeterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pGMeter))
            )
            .andExpect(status().isBadRequest());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPGMeter() throws Exception {
        int databaseSizeBeforeUpdate = pGMeterRepository.findAll().size();
        pGMeter.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPGMeterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pGMeter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PGMeter in the database
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePGMeter() throws Exception {
        // Initialize the database
        pGMeterRepository.saveAndFlush(pGMeter);

        int databaseSizeBeforeDelete = pGMeterRepository.findAll().size();

        // Delete the pGMeter
        restPGMeterMockMvc
            .perform(delete(ENTITY_API_URL_ID, pGMeter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PGMeter> pGMeterList = pGMeterRepository.findAll();
        assertThat(pGMeterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
