package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.PuestoDeTrabajo;
import com.pronix.sbc.repository.PuestoDeTrabajoRepository;
import com.pronix.sbc.service.PuestoDeTrabajoService;
import com.pronix.sbc.service.dto.PuestoDeTrabajoDTO;
import com.pronix.sbc.service.mapper.PuestoDeTrabajoMapper;
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
import java.util.ArrayList;
import java.util.List;

import static com.pronix.sbc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PuestoDeTrabajoResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class PuestoDeTrabajoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private PuestoDeTrabajoRepository puestoDeTrabajoRepository;

    @Mock
    private PuestoDeTrabajoRepository puestoDeTrabajoRepositoryMock;

    @Autowired
    private PuestoDeTrabajoMapper puestoDeTrabajoMapper;

    @Mock
    private PuestoDeTrabajoService puestoDeTrabajoServiceMock;

    @Autowired
    private PuestoDeTrabajoService puestoDeTrabajoService;

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

    private MockMvc restPuestoDeTrabajoMockMvc;

    private PuestoDeTrabajo puestoDeTrabajo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuestoDeTrabajoResource puestoDeTrabajoResource = new PuestoDeTrabajoResource(puestoDeTrabajoService);
        this.restPuestoDeTrabajoMockMvc = MockMvcBuilders.standaloneSetup(puestoDeTrabajoResource)
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
    public static PuestoDeTrabajo createEntity(EntityManager em) {
        PuestoDeTrabajo puestoDeTrabajo = new PuestoDeTrabajo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return puestoDeTrabajo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuestoDeTrabajo createUpdatedEntity(EntityManager em) {
        PuestoDeTrabajo puestoDeTrabajo = new PuestoDeTrabajo()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        return puestoDeTrabajo;
    }

    @BeforeEach
    public void initTest() {
        puestoDeTrabajo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuestoDeTrabajo() throws Exception {
        int databaseSizeBeforeCreate = puestoDeTrabajoRepository.findAll().size();

        // Create the PuestoDeTrabajo
        PuestoDeTrabajoDTO puestoDeTrabajoDTO = puestoDeTrabajoMapper.toDto(puestoDeTrabajo);
        restPuestoDeTrabajoMockMvc.perform(post("/api/puesto-de-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDeTrabajoDTO)))
            .andExpect(status().isCreated());

        // Validate the PuestoDeTrabajo in the database
        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeCreate + 1);
        PuestoDeTrabajo testPuestoDeTrabajo = puestoDeTrabajoList.get(puestoDeTrabajoList.size() - 1);
        assertThat(testPuestoDeTrabajo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPuestoDeTrabajo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createPuestoDeTrabajoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puestoDeTrabajoRepository.findAll().size();

        // Create the PuestoDeTrabajo with an existing ID
        puestoDeTrabajo.setId(1L);
        PuestoDeTrabajoDTO puestoDeTrabajoDTO = puestoDeTrabajoMapper.toDto(puestoDeTrabajo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuestoDeTrabajoMockMvc.perform(post("/api/puesto-de-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDeTrabajoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PuestoDeTrabajo in the database
        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = puestoDeTrabajoRepository.findAll().size();
        // set the field null
        puestoDeTrabajo.setNombre(null);

        // Create the PuestoDeTrabajo, which fails.
        PuestoDeTrabajoDTO puestoDeTrabajoDTO = puestoDeTrabajoMapper.toDto(puestoDeTrabajo);

        restPuestoDeTrabajoMockMvc.perform(post("/api/puesto-de-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDeTrabajoDTO)))
            .andExpect(status().isBadRequest());

        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = puestoDeTrabajoRepository.findAll().size();
        // set the field null
        puestoDeTrabajo.setDescripcion(null);

        // Create the PuestoDeTrabajo, which fails.
        PuestoDeTrabajoDTO puestoDeTrabajoDTO = puestoDeTrabajoMapper.toDto(puestoDeTrabajo);

        restPuestoDeTrabajoMockMvc.perform(post("/api/puesto-de-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDeTrabajoDTO)))
            .andExpect(status().isBadRequest());

        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPuestoDeTrabajos() throws Exception {
        // Initialize the database
        puestoDeTrabajoRepository.saveAndFlush(puestoDeTrabajo);

        // Get all the puestoDeTrabajoList
        restPuestoDeTrabajoMockMvc.perform(get("/api/puesto-de-trabajos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puestoDeTrabajo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPuestoDeTrabajosWithEagerRelationshipsIsEnabled() throws Exception {
        PuestoDeTrabajoResource puestoDeTrabajoResource = new PuestoDeTrabajoResource(puestoDeTrabajoServiceMock);
        when(puestoDeTrabajoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPuestoDeTrabajoMockMvc = MockMvcBuilders.standaloneSetup(puestoDeTrabajoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPuestoDeTrabajoMockMvc.perform(get("/api/puesto-de-trabajos?eagerload=true"))
        .andExpect(status().isOk());

        verify(puestoDeTrabajoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPuestoDeTrabajosWithEagerRelationshipsIsNotEnabled() throws Exception {
        PuestoDeTrabajoResource puestoDeTrabajoResource = new PuestoDeTrabajoResource(puestoDeTrabajoServiceMock);
            when(puestoDeTrabajoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPuestoDeTrabajoMockMvc = MockMvcBuilders.standaloneSetup(puestoDeTrabajoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPuestoDeTrabajoMockMvc.perform(get("/api/puesto-de-trabajos?eagerload=true"))
        .andExpect(status().isOk());

            verify(puestoDeTrabajoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPuestoDeTrabajo() throws Exception {
        // Initialize the database
        puestoDeTrabajoRepository.saveAndFlush(puestoDeTrabajo);

        // Get the puestoDeTrabajo
        restPuestoDeTrabajoMockMvc.perform(get("/api/puesto-de-trabajos/{id}", puestoDeTrabajo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puestoDeTrabajo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    public void getNonExistingPuestoDeTrabajo() throws Exception {
        // Get the puestoDeTrabajo
        restPuestoDeTrabajoMockMvc.perform(get("/api/puesto-de-trabajos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuestoDeTrabajo() throws Exception {
        // Initialize the database
        puestoDeTrabajoRepository.saveAndFlush(puestoDeTrabajo);

        int databaseSizeBeforeUpdate = puestoDeTrabajoRepository.findAll().size();

        // Update the puestoDeTrabajo
        PuestoDeTrabajo updatedPuestoDeTrabajo = puestoDeTrabajoRepository.findById(puestoDeTrabajo.getId()).get();
        // Disconnect from session so that the updates on updatedPuestoDeTrabajo are not directly saved in db
        em.detach(updatedPuestoDeTrabajo);
        updatedPuestoDeTrabajo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        PuestoDeTrabajoDTO puestoDeTrabajoDTO = puestoDeTrabajoMapper.toDto(updatedPuestoDeTrabajo);

        restPuestoDeTrabajoMockMvc.perform(put("/api/puesto-de-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDeTrabajoDTO)))
            .andExpect(status().isOk());

        // Validate the PuestoDeTrabajo in the database
        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeUpdate);
        PuestoDeTrabajo testPuestoDeTrabajo = puestoDeTrabajoList.get(puestoDeTrabajoList.size() - 1);
        assertThat(testPuestoDeTrabajo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPuestoDeTrabajo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingPuestoDeTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = puestoDeTrabajoRepository.findAll().size();

        // Create the PuestoDeTrabajo
        PuestoDeTrabajoDTO puestoDeTrabajoDTO = puestoDeTrabajoMapper.toDto(puestoDeTrabajo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuestoDeTrabajoMockMvc.perform(put("/api/puesto-de-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDeTrabajoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PuestoDeTrabajo in the database
        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuestoDeTrabajo() throws Exception {
        // Initialize the database
        puestoDeTrabajoRepository.saveAndFlush(puestoDeTrabajo);

        int databaseSizeBeforeDelete = puestoDeTrabajoRepository.findAll().size();

        // Delete the puestoDeTrabajo
        restPuestoDeTrabajoMockMvc.perform(delete("/api/puesto-de-trabajos/{id}", puestoDeTrabajo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PuestoDeTrabajo> puestoDeTrabajoList = puestoDeTrabajoRepository.findAll();
        assertThat(puestoDeTrabajoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuestoDeTrabajo.class);
        PuestoDeTrabajo puestoDeTrabajo1 = new PuestoDeTrabajo();
        puestoDeTrabajo1.setId(1L);
        PuestoDeTrabajo puestoDeTrabajo2 = new PuestoDeTrabajo();
        puestoDeTrabajo2.setId(puestoDeTrabajo1.getId());
        assertThat(puestoDeTrabajo1).isEqualTo(puestoDeTrabajo2);
        puestoDeTrabajo2.setId(2L);
        assertThat(puestoDeTrabajo1).isNotEqualTo(puestoDeTrabajo2);
        puestoDeTrabajo1.setId(null);
        assertThat(puestoDeTrabajo1).isNotEqualTo(puestoDeTrabajo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuestoDeTrabajoDTO.class);
        PuestoDeTrabajoDTO puestoDeTrabajoDTO1 = new PuestoDeTrabajoDTO();
        puestoDeTrabajoDTO1.setId(1L);
        PuestoDeTrabajoDTO puestoDeTrabajoDTO2 = new PuestoDeTrabajoDTO();
        assertThat(puestoDeTrabajoDTO1).isNotEqualTo(puestoDeTrabajoDTO2);
        puestoDeTrabajoDTO2.setId(puestoDeTrabajoDTO1.getId());
        assertThat(puestoDeTrabajoDTO1).isEqualTo(puestoDeTrabajoDTO2);
        puestoDeTrabajoDTO2.setId(2L);
        assertThat(puestoDeTrabajoDTO1).isNotEqualTo(puestoDeTrabajoDTO2);
        puestoDeTrabajoDTO1.setId(null);
        assertThat(puestoDeTrabajoDTO1).isNotEqualTo(puestoDeTrabajoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(puestoDeTrabajoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(puestoDeTrabajoMapper.fromId(null)).isNull();
    }
}
