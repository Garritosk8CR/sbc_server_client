package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.CarreraProfesional;
import com.pronix.sbc.repository.CarreraProfesionalRepository;
import com.pronix.sbc.service.CarreraProfesionalService;
import com.pronix.sbc.service.dto.CarreraProfesionalDTO;
import com.pronix.sbc.service.mapper.CarreraProfesionalMapper;
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
 * Integration tests for the {@link CarreraProfesionalResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class CarreraProfesionalResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private CarreraProfesionalRepository carreraProfesionalRepository;

    @Autowired
    private CarreraProfesionalMapper carreraProfesionalMapper;

    @Autowired
    private CarreraProfesionalService carreraProfesionalService;

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

    private MockMvc restCarreraProfesionalMockMvc;

    private CarreraProfesional carreraProfesional;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarreraProfesionalResource carreraProfesionalResource = new CarreraProfesionalResource(carreraProfesionalService);
        this.restCarreraProfesionalMockMvc = MockMvcBuilders.standaloneSetup(carreraProfesionalResource)
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
    public static CarreraProfesional createEntity(EntityManager em) {
        CarreraProfesional carreraProfesional = new CarreraProfesional()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return carreraProfesional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarreraProfesional createUpdatedEntity(EntityManager em) {
        CarreraProfesional carreraProfesional = new CarreraProfesional()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        return carreraProfesional;
    }

    @BeforeEach
    public void initTest() {
        carreraProfesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarreraProfesional() throws Exception {
        int databaseSizeBeforeCreate = carreraProfesionalRepository.findAll().size();

        // Create the CarreraProfesional
        CarreraProfesionalDTO carreraProfesionalDTO = carreraProfesionalMapper.toDto(carreraProfesional);
        restCarreraProfesionalMockMvc.perform(post("/api/carrera-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraProfesionalDTO)))
            .andExpect(status().isCreated());

        // Validate the CarreraProfesional in the database
        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeCreate + 1);
        CarreraProfesional testCarreraProfesional = carreraProfesionalList.get(carreraProfesionalList.size() - 1);
        assertThat(testCarreraProfesional.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCarreraProfesional.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createCarreraProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carreraProfesionalRepository.findAll().size();

        // Create the CarreraProfesional with an existing ID
        carreraProfesional.setId(1L);
        CarreraProfesionalDTO carreraProfesionalDTO = carreraProfesionalMapper.toDto(carreraProfesional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarreraProfesionalMockMvc.perform(post("/api/carrera-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraProfesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarreraProfesional in the database
        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = carreraProfesionalRepository.findAll().size();
        // set the field null
        carreraProfesional.setNombre(null);

        // Create the CarreraProfesional, which fails.
        CarreraProfesionalDTO carreraProfesionalDTO = carreraProfesionalMapper.toDto(carreraProfesional);

        restCarreraProfesionalMockMvc.perform(post("/api/carrera-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraProfesionalDTO)))
            .andExpect(status().isBadRequest());

        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = carreraProfesionalRepository.findAll().size();
        // set the field null
        carreraProfesional.setDescripcion(null);

        // Create the CarreraProfesional, which fails.
        CarreraProfesionalDTO carreraProfesionalDTO = carreraProfesionalMapper.toDto(carreraProfesional);

        restCarreraProfesionalMockMvc.perform(post("/api/carrera-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraProfesionalDTO)))
            .andExpect(status().isBadRequest());

        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarreraProfesionals() throws Exception {
        // Initialize the database
        carreraProfesionalRepository.saveAndFlush(carreraProfesional);

        // Get all the carreraProfesionalList
        restCarreraProfesionalMockMvc.perform(get("/api/carrera-profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carreraProfesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getCarreraProfesional() throws Exception {
        // Initialize the database
        carreraProfesionalRepository.saveAndFlush(carreraProfesional);

        // Get the carreraProfesional
        restCarreraProfesionalMockMvc.perform(get("/api/carrera-profesionals/{id}", carreraProfesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carreraProfesional.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    public void getNonExistingCarreraProfesional() throws Exception {
        // Get the carreraProfesional
        restCarreraProfesionalMockMvc.perform(get("/api/carrera-profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarreraProfesional() throws Exception {
        // Initialize the database
        carreraProfesionalRepository.saveAndFlush(carreraProfesional);

        int databaseSizeBeforeUpdate = carreraProfesionalRepository.findAll().size();

        // Update the carreraProfesional
        CarreraProfesional updatedCarreraProfesional = carreraProfesionalRepository.findById(carreraProfesional.getId()).get();
        // Disconnect from session so that the updates on updatedCarreraProfesional are not directly saved in db
        em.detach(updatedCarreraProfesional);
        updatedCarreraProfesional
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        CarreraProfesionalDTO carreraProfesionalDTO = carreraProfesionalMapper.toDto(updatedCarreraProfesional);

        restCarreraProfesionalMockMvc.perform(put("/api/carrera-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraProfesionalDTO)))
            .andExpect(status().isOk());

        // Validate the CarreraProfesional in the database
        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeUpdate);
        CarreraProfesional testCarreraProfesional = carreraProfesionalList.get(carreraProfesionalList.size() - 1);
        assertThat(testCarreraProfesional.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCarreraProfesional.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCarreraProfesional() throws Exception {
        int databaseSizeBeforeUpdate = carreraProfesionalRepository.findAll().size();

        // Create the CarreraProfesional
        CarreraProfesionalDTO carreraProfesionalDTO = carreraProfesionalMapper.toDto(carreraProfesional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarreraProfesionalMockMvc.perform(put("/api/carrera-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carreraProfesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarreraProfesional in the database
        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarreraProfesional() throws Exception {
        // Initialize the database
        carreraProfesionalRepository.saveAndFlush(carreraProfesional);

        int databaseSizeBeforeDelete = carreraProfesionalRepository.findAll().size();

        // Delete the carreraProfesional
        restCarreraProfesionalMockMvc.perform(delete("/api/carrera-profesionals/{id}", carreraProfesional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarreraProfesional> carreraProfesionalList = carreraProfesionalRepository.findAll();
        assertThat(carreraProfesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraProfesional.class);
        CarreraProfesional carreraProfesional1 = new CarreraProfesional();
        carreraProfesional1.setId(1L);
        CarreraProfesional carreraProfesional2 = new CarreraProfesional();
        carreraProfesional2.setId(carreraProfesional1.getId());
        assertThat(carreraProfesional1).isEqualTo(carreraProfesional2);
        carreraProfesional2.setId(2L);
        assertThat(carreraProfesional1).isNotEqualTo(carreraProfesional2);
        carreraProfesional1.setId(null);
        assertThat(carreraProfesional1).isNotEqualTo(carreraProfesional2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarreraProfesionalDTO.class);
        CarreraProfesionalDTO carreraProfesionalDTO1 = new CarreraProfesionalDTO();
        carreraProfesionalDTO1.setId(1L);
        CarreraProfesionalDTO carreraProfesionalDTO2 = new CarreraProfesionalDTO();
        assertThat(carreraProfesionalDTO1).isNotEqualTo(carreraProfesionalDTO2);
        carreraProfesionalDTO2.setId(carreraProfesionalDTO1.getId());
        assertThat(carreraProfesionalDTO1).isEqualTo(carreraProfesionalDTO2);
        carreraProfesionalDTO2.setId(2L);
        assertThat(carreraProfesionalDTO1).isNotEqualTo(carreraProfesionalDTO2);
        carreraProfesionalDTO1.setId(null);
        assertThat(carreraProfesionalDTO1).isNotEqualTo(carreraProfesionalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carreraProfesionalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carreraProfesionalMapper.fromId(null)).isNull();
    }
}
