package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.Suscripcion;
import com.pronix.sbc.repository.SuscripcionRepository;
import com.pronix.sbc.service.SuscripcionService;
import com.pronix.sbc.service.dto.SuscripcionDTO;
import com.pronix.sbc.service.mapper.SuscripcionMapper;
import com.pronix.sbc.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.pronix.sbc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pronix.sbc.domain.enumeration.EstadoSuscripcion;
/**
 * Integration tests for the {@link SuscripcionResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class SuscripcionResourceIT {

    private static final LocalDate DEFAULT_FECHA_SUSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SUSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final EstadoSuscripcion DEFAULT_ESTADO_SUSCRIPCION = EstadoSuscripcion.ACTIVO;
    private static final EstadoSuscripcion UPDATED_ESTADO_SUSCRIPCION = EstadoSuscripcion.INACTIVO;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Mock
    private SuscripcionRepository suscripcionRepositoryMock;

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    @Mock
    private SuscripcionService suscripcionServiceMock;

    @Autowired
    private SuscripcionService suscripcionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSuscripcionMockMvc;

    private Suscripcion suscripcion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuscripcionResource suscripcionResource = new SuscripcionResource(suscripcionService);
        this.restSuscripcionMockMvc = MockMvcBuilders.standaloneSetup(suscripcionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suscripcion createEntity(EntityManager em) {
        Suscripcion suscripcion = new Suscripcion()
            .fechaSuscripcion(DEFAULT_FECHA_SUSCRIPCION)
            .estadoSuscripcion(DEFAULT_ESTADO_SUSCRIPCION);
        return suscripcion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suscripcion createUpdatedEntity(EntityManager em) {
        Suscripcion suscripcion = new Suscripcion()
            .fechaSuscripcion(UPDATED_FECHA_SUSCRIPCION)
            .estadoSuscripcion(UPDATED_ESTADO_SUSCRIPCION);
        return suscripcion;
    }

    @BeforeEach
    public void initTest() {
        suscripcion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuscripcion() throws Exception {
        int databaseSizeBeforeCreate = suscripcionRepository.findAll().size();

        // Create the Suscripcion
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(suscripcion);
        restSuscripcionMockMvc.perform(post("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isCreated());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeCreate + 1);
        Suscripcion testSuscripcion = suscripcionList.get(suscripcionList.size() - 1);
        assertThat(testSuscripcion.getFechaSuscripcion()).isEqualTo(DEFAULT_FECHA_SUSCRIPCION);
        assertThat(testSuscripcion.getEstadoSuscripcion()).isEqualTo(DEFAULT_ESTADO_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void createSuscripcionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suscripcionRepository.findAll().size();

        // Create the Suscripcion with an existing ID
        suscripcion.setId(1L);
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(suscripcion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuscripcionMockMvc.perform(post("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSuscripcions() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        // Get all the suscripcionList
        restSuscripcionMockMvc.perform(get("/api/suscripcions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suscripcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaSuscripcion").value(hasItem(DEFAULT_FECHA_SUSCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estadoSuscripcion").value(hasItem(DEFAULT_ESTADO_SUSCRIPCION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSuscripcionsWithEagerRelationshipsIsEnabled() throws Exception {
        SuscripcionResource suscripcionResource = new SuscripcionResource(suscripcionServiceMock);
        when(suscripcionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSuscripcionMockMvc = MockMvcBuilders.standaloneSetup(suscripcionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSuscripcionMockMvc.perform(get("/api/suscripcions?eagerload=true"))
        .andExpect(status().isOk());

        verify(suscripcionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSuscripcionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SuscripcionResource suscripcionResource = new SuscripcionResource(suscripcionServiceMock);
            when(suscripcionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSuscripcionMockMvc = MockMvcBuilders.standaloneSetup(suscripcionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSuscripcionMockMvc.perform(get("/api/suscripcions?eagerload=true"))
        .andExpect(status().isOk());

            verify(suscripcionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSuscripcion() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        // Get the suscripcion
        restSuscripcionMockMvc.perform(get("/api/suscripcions/{id}", suscripcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suscripcion.getId().intValue()))
            .andExpect(jsonPath("$.fechaSuscripcion").value(DEFAULT_FECHA_SUSCRIPCION.toString()))
            .andExpect(jsonPath("$.estadoSuscripcion").value(DEFAULT_ESTADO_SUSCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuscripcion() throws Exception {
        // Get the suscripcion
        restSuscripcionMockMvc.perform(get("/api/suscripcions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuscripcion() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        int databaseSizeBeforeUpdate = suscripcionRepository.findAll().size();

        // Update the suscripcion
        Suscripcion updatedSuscripcion = suscripcionRepository.findById(suscripcion.getId()).get();
        // Disconnect from session so that the updates on updatedSuscripcion are not directly saved in db
        em.detach(updatedSuscripcion);
        updatedSuscripcion
            .fechaSuscripcion(UPDATED_FECHA_SUSCRIPCION)
            .estadoSuscripcion(UPDATED_ESTADO_SUSCRIPCION);
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(updatedSuscripcion);

        restSuscripcionMockMvc.perform(put("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isOk());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeUpdate);
        Suscripcion testSuscripcion = suscripcionList.get(suscripcionList.size() - 1);
        assertThat(testSuscripcion.getFechaSuscripcion()).isEqualTo(UPDATED_FECHA_SUSCRIPCION);
        assertThat(testSuscripcion.getEstadoSuscripcion()).isEqualTo(UPDATED_ESTADO_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingSuscripcion() throws Exception {
        int databaseSizeBeforeUpdate = suscripcionRepository.findAll().size();

        // Create the Suscripcion
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(suscripcion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuscripcionMockMvc.perform(put("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuscripcion() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        int databaseSizeBeforeDelete = suscripcionRepository.findAll().size();

        // Delete the suscripcion
        restSuscripcionMockMvc.perform(delete("/api/suscripcions/{id}", suscripcion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suscripcion.class);
        Suscripcion suscripcion1 = new Suscripcion();
        suscripcion1.setId(1L);
        Suscripcion suscripcion2 = new Suscripcion();
        suscripcion2.setId(suscripcion1.getId());
        assertThat(suscripcion1).isEqualTo(suscripcion2);
        suscripcion2.setId(2L);
        assertThat(suscripcion1).isNotEqualTo(suscripcion2);
        suscripcion1.setId(null);
        assertThat(suscripcion1).isNotEqualTo(suscripcion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuscripcionDTO.class);
        SuscripcionDTO suscripcionDTO1 = new SuscripcionDTO();
        suscripcionDTO1.setId(1L);
        SuscripcionDTO suscripcionDTO2 = new SuscripcionDTO();
        assertThat(suscripcionDTO1).isNotEqualTo(suscripcionDTO2);
        suscripcionDTO2.setId(suscripcionDTO1.getId());
        assertThat(suscripcionDTO1).isEqualTo(suscripcionDTO2);
        suscripcionDTO2.setId(2L);
        assertThat(suscripcionDTO1).isNotEqualTo(suscripcionDTO2);
        suscripcionDTO1.setId(null);
        assertThat(suscripcionDTO1).isNotEqualTo(suscripcionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(suscripcionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(suscripcionMapper.fromId(null)).isNull();
    }
}
