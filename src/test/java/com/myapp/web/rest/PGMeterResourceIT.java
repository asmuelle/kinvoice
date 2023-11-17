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

    private static final Float DEFAULT_LON = 1F;
    private static final Float UPDATED_LON = 2F;

    private static final Float DEFAULT_WS_LAT = 1F;
    private static final Float UPDATED_WS_LAT = 2F;

    private static final Float DEFAULT_WS_LON = 1F;
    private static final Float UPDATED_WS_LON = 2F;

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;

    private static final Float DEFAULT_KM = 1F;
    private static final Float UPDATED_KM = 2F;

    private static final String DEFAULT_PEER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PEER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MARKERS = "AAAAAAAAAA";
    private static final String UPDATED_MARKERS = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

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
