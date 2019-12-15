package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.Requisito;
import com.pronix.sbc.repository.RequisitoRepository;
import com.pronix.sbc.service.RequisitoService;
import com.pronix.sbc.service.dto.RequisitoDTO;
import com.pronix.sbc.service.mapper.RequisitoMapper;
import com.pronix.sbc.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pronix.sbc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RequisitoResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class RequisitoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private RequisitoRepository requisitoRepository;

    @Autowired
    private RequisitoMapper requisitoMapper;

    @Autowired
    private RequisitoService requisitoService;

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

    private MockMvc restRequisitoMockMvc;

    private Requisito requisito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequisitoResource requisitoResource = new RequisitoResource(requisitoService);
        this.restRequisitoMockMvc = MockMvcBuilders.standaloneSetup(requisitoResource)
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
    public static Requisito createEntity(EntityManager em) {
        Requisito requisito = new Requisito()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .tipo(DEFAULT_TIPO);
        return requisito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requisito createUpdatedEntity(EntityManager em) {
        Requisito requisito = new Requisito()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipo(UPDATED_TIPO);
        return requisito;
    }

    @BeforeEach
    public void initTest() {
        requisito = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisito() throws Exception {
        int databaseSizeBeforeCreate = requisitoRepository.findAll().size();

        // Create the Requisito
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(requisito);
        restRequisitoMockMvc.perform(post("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isCreated());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeCreate + 1);
        Requisito testRequisito = requisitoList.get(requisitoList.size() - 1);
        assertThat(testRequisito.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRequisito.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRequisito.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createRequisitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitoRepository.findAll().size();

        // Create the Requisito with an existing ID
        requisito.setId(1L);
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(requisito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitoMockMvc.perform(post("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitoRepository.findAll().size();
        // set the field null
        requisito.setNombre(null);

        // Create the Requisito, which fails.
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(requisito);

        restRequisitoMockMvc.perform(post("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isBadRequest());

        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitoRepository.findAll().size();
        // set the field null
        requisito.setDescripcion(null);

        // Create the Requisito, which fails.
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(requisito);

        restRequisitoMockMvc.perform(post("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isBadRequest());

        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitoRepository.findAll().size();
        // set the field null
        requisito.setTipo(null);

        // Create the Requisito, which fails.
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(requisito);

        restRequisitoMockMvc.perform(post("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isBadRequest());

        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequisitos() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        // Get all the requisitoList
        restRequisitoMockMvc.perform(get("/api/requisitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }
    
    @Test
    @Transactional
    public void getRequisito() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        // Get the requisito
        restRequisitoMockMvc.perform(get("/api/requisitos/{id}", requisito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisito.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }

    @Test
    @Transactional
    public void getNonExistingRequisito() throws Exception {
        // Get the requisito
        restRequisitoMockMvc.perform(get("/api/requisitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisito() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();

        // Update the requisito
        Requisito updatedRequisito = requisitoRepository.findById(requisito.getId()).get();
        // Disconnect from session so that the updates on updatedRequisito are not directly saved in db
        em.detach(updatedRequisito);
        updatedRequisito
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipo(UPDATED_TIPO);
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(updatedRequisito);

        restRequisitoMockMvc.perform(put("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isOk());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
        Requisito testRequisito = requisitoList.get(requisitoList.size() - 1);
        assertThat(testRequisito.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRequisito.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRequisito.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();

        // Create the Requisito
        RequisitoDTO requisitoDTO = requisitoMapper.toDto(requisito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitoMockMvc.perform(put("/api/requisitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequisito() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        int databaseSizeBeforeDelete = requisitoRepository.findAll().size();

        // Delete the requisito
        restRequisitoMockMvc.perform(delete("/api/requisitos/{id}", requisito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requisito.class);
        Requisito requisito1 = new Requisito();
        requisito1.setId(1L);
        Requisito requisito2 = new Requisito();
        requisito2.setId(requisito1.getId());
        assertThat(requisito1).isEqualTo(requisito2);
        requisito2.setId(2L);
        assertThat(requisito1).isNotEqualTo(requisito2);
        requisito1.setId(null);
        assertThat(requisito1).isNotEqualTo(requisito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitoDTO.class);
        RequisitoDTO requisitoDTO1 = new RequisitoDTO();
        requisitoDTO1.setId(1L);
        RequisitoDTO requisitoDTO2 = new RequisitoDTO();
        assertThat(requisitoDTO1).isNotEqualTo(requisitoDTO2);
        requisitoDTO2.setId(requisitoDTO1.getId());
        assertThat(requisitoDTO1).isEqualTo(requisitoDTO2);
        requisitoDTO2.setId(2L);
        assertThat(requisitoDTO1).isNotEqualTo(requisitoDTO2);
        requisitoDTO1.setId(null);
        assertThat(requisitoDTO1).isNotEqualTo(requisitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requisitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requisitoMapper.fromId(null)).isNull();
    }
}
