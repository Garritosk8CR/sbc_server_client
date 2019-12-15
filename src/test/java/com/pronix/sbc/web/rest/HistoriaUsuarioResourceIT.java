package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.HistoriaUsuario;
import com.pronix.sbc.repository.HistoriaUsuarioRepository;
import com.pronix.sbc.service.HistoriaUsuarioService;
import com.pronix.sbc.service.dto.HistoriaUsuarioDTO;
import com.pronix.sbc.service.mapper.HistoriaUsuarioMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.pronix.sbc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HistoriaUsuarioResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class HistoriaUsuarioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_CONCLUCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CONCLUCION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SPRINT = "AAAAAAAAAA";
    private static final String UPDATED_SPRINT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EPIC = false;
    private static final Boolean UPDATED_IS_EPIC = true;

    @Autowired
    private HistoriaUsuarioRepository historiaUsuarioRepository;

    @Autowired
    private HistoriaUsuarioMapper historiaUsuarioMapper;

    @Autowired
    private HistoriaUsuarioService historiaUsuarioService;

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

    private MockMvc restHistoriaUsuarioMockMvc;

    private HistoriaUsuario historiaUsuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoriaUsuarioResource historiaUsuarioResource = new HistoriaUsuarioResource(historiaUsuarioService);
        this.restHistoriaUsuarioMockMvc = MockMvcBuilders.standaloneSetup(historiaUsuarioResource)
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
    public static HistoriaUsuario createEntity(EntityManager em) {
        HistoriaUsuario historiaUsuario = new HistoriaUsuario()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaConclucion(DEFAULT_FECHA_CONCLUCION)
            .sprint(DEFAULT_SPRINT)
            .isEpic(DEFAULT_IS_EPIC);
        return historiaUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriaUsuario createUpdatedEntity(EntityManager em) {
        HistoriaUsuario historiaUsuario = new HistoriaUsuario()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaConclucion(UPDATED_FECHA_CONCLUCION)
            .sprint(UPDATED_SPRINT)
            .isEpic(UPDATED_IS_EPIC);
        return historiaUsuario;
    }

    @BeforeEach
    public void initTest() {
        historiaUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriaUsuario() throws Exception {
        int databaseSizeBeforeCreate = historiaUsuarioRepository.findAll().size();

        // Create the HistoriaUsuario
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(historiaUsuario);
        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriaUsuario testHistoriaUsuario = historiaUsuarioList.get(historiaUsuarioList.size() - 1);
        assertThat(testHistoriaUsuario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testHistoriaUsuario.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testHistoriaUsuario.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testHistoriaUsuario.getFechaConclucion()).isEqualTo(DEFAULT_FECHA_CONCLUCION);
        assertThat(testHistoriaUsuario.getSprint()).isEqualTo(DEFAULT_SPRINT);
        assertThat(testHistoriaUsuario.isIsEpic()).isEqualTo(DEFAULT_IS_EPIC);
    }

    @Test
    @Transactional
    public void createHistoriaUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiaUsuarioRepository.findAll().size();

        // Create the HistoriaUsuario with an existing ID
        historiaUsuario.setId(1L);
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(historiaUsuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaUsuarioRepository.findAll().size();
        // set the field null
        historiaUsuario.setNombre(null);

        // Create the HistoriaUsuario, which fails.
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(historiaUsuario);

        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaUsuarioRepository.findAll().size();
        // set the field null
        historiaUsuario.setDescripcion(null);

        // Create the HistoriaUsuario, which fails.
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(historiaUsuario);

        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaUsuarioRepository.findAll().size();
        // set the field null
        historiaUsuario.setFechaCreacion(null);

        // Create the HistoriaUsuario, which fails.
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(historiaUsuario);

        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoriaUsuarios() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        // Get all the historiaUsuarioList
        restHistoriaUsuarioMockMvc.perform(get("/api/historia-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiaUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaConclucion").value(hasItem(DEFAULT_FECHA_CONCLUCION.toString())))
            .andExpect(jsonPath("$.[*].sprint").value(hasItem(DEFAULT_SPRINT)))
            .andExpect(jsonPath("$.[*].isEpic").value(hasItem(DEFAULT_IS_EPIC.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getHistoriaUsuario() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        // Get the historiaUsuario
        restHistoriaUsuarioMockMvc.perform(get("/api/historia-usuarios/{id}", historiaUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historiaUsuario.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaConclucion").value(DEFAULT_FECHA_CONCLUCION.toString()))
            .andExpect(jsonPath("$.sprint").value(DEFAULT_SPRINT))
            .andExpect(jsonPath("$.isEpic").value(DEFAULT_IS_EPIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoriaUsuario() throws Exception {
        // Get the historiaUsuario
        restHistoriaUsuarioMockMvc.perform(get("/api/historia-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriaUsuario() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        int databaseSizeBeforeUpdate = historiaUsuarioRepository.findAll().size();

        // Update the historiaUsuario
        HistoriaUsuario updatedHistoriaUsuario = historiaUsuarioRepository.findById(historiaUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriaUsuario are not directly saved in db
        em.detach(updatedHistoriaUsuario);
        updatedHistoriaUsuario
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaConclucion(UPDATED_FECHA_CONCLUCION)
            .sprint(UPDATED_SPRINT)
            .isEpic(UPDATED_IS_EPIC);
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(updatedHistoriaUsuario);

        restHistoriaUsuarioMockMvc.perform(put("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isOk());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeUpdate);
        HistoriaUsuario testHistoriaUsuario = historiaUsuarioList.get(historiaUsuarioList.size() - 1);
        assertThat(testHistoriaUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHistoriaUsuario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testHistoriaUsuario.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testHistoriaUsuario.getFechaConclucion()).isEqualTo(UPDATED_FECHA_CONCLUCION);
        assertThat(testHistoriaUsuario.getSprint()).isEqualTo(UPDATED_SPRINT);
        assertThat(testHistoriaUsuario.isIsEpic()).isEqualTo(UPDATED_IS_EPIC);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriaUsuario() throws Exception {
        int databaseSizeBeforeUpdate = historiaUsuarioRepository.findAll().size();

        // Create the HistoriaUsuario
        HistoriaUsuarioDTO historiaUsuarioDTO = historiaUsuarioMapper.toDto(historiaUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriaUsuarioMockMvc.perform(put("/api/historia-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoriaUsuario() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        int databaseSizeBeforeDelete = historiaUsuarioRepository.findAll().size();

        // Delete the historiaUsuario
        restHistoriaUsuarioMockMvc.perform(delete("/api/historia-usuarios/{id}", historiaUsuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaUsuario.class);
        HistoriaUsuario historiaUsuario1 = new HistoriaUsuario();
        historiaUsuario1.setId(1L);
        HistoriaUsuario historiaUsuario2 = new HistoriaUsuario();
        historiaUsuario2.setId(historiaUsuario1.getId());
        assertThat(historiaUsuario1).isEqualTo(historiaUsuario2);
        historiaUsuario2.setId(2L);
        assertThat(historiaUsuario1).isNotEqualTo(historiaUsuario2);
        historiaUsuario1.setId(null);
        assertThat(historiaUsuario1).isNotEqualTo(historiaUsuario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaUsuarioDTO.class);
        HistoriaUsuarioDTO historiaUsuarioDTO1 = new HistoriaUsuarioDTO();
        historiaUsuarioDTO1.setId(1L);
        HistoriaUsuarioDTO historiaUsuarioDTO2 = new HistoriaUsuarioDTO();
        assertThat(historiaUsuarioDTO1).isNotEqualTo(historiaUsuarioDTO2);
        historiaUsuarioDTO2.setId(historiaUsuarioDTO1.getId());
        assertThat(historiaUsuarioDTO1).isEqualTo(historiaUsuarioDTO2);
        historiaUsuarioDTO2.setId(2L);
        assertThat(historiaUsuarioDTO1).isNotEqualTo(historiaUsuarioDTO2);
        historiaUsuarioDTO1.setId(null);
        assertThat(historiaUsuarioDTO1).isNotEqualTo(historiaUsuarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(historiaUsuarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(historiaUsuarioMapper.fromId(null)).isNull();
    }
}
