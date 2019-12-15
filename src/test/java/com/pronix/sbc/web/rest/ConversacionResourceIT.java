package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.Conversacion;
import com.pronix.sbc.repository.ConversacionRepository;
import com.pronix.sbc.service.ConversacionService;
import com.pronix.sbc.service.dto.ConversacionDTO;
import com.pronix.sbc.service.mapper.ConversacionMapper;
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
 * Integration tests for the {@link ConversacionResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class ConversacionResourceIT {

    private static final String DEFAULT_USUARIO_1 = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO_2 = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_2 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_DE_CONVERSACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_CONVERSACION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private ConversacionMapper conversacionMapper;

    @Autowired
    private ConversacionService conversacionService;

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

    private MockMvc restConversacionMockMvc;

    private Conversacion conversacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConversacionResource conversacionResource = new ConversacionResource(conversacionService);
        this.restConversacionMockMvc = MockMvcBuilders.standaloneSetup(conversacionResource)
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
    public static Conversacion createEntity(EntityManager em) {
        Conversacion conversacion = new Conversacion()
            .usuario1(DEFAULT_USUARIO_1)
            .usuario2(DEFAULT_USUARIO_2)
            .fechaDeConversacion(DEFAULT_FECHA_DE_CONVERSACION);
        return conversacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversacion createUpdatedEntity(EntityManager em) {
        Conversacion conversacion = new Conversacion()
            .usuario1(UPDATED_USUARIO_1)
            .usuario2(UPDATED_USUARIO_2)
            .fechaDeConversacion(UPDATED_FECHA_DE_CONVERSACION);
        return conversacion;
    }

    @BeforeEach
    public void initTest() {
        conversacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversacion() throws Exception {
        int databaseSizeBeforeCreate = conversacionRepository.findAll().size();

        // Create the Conversacion
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(conversacion);
        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeCreate + 1);
        Conversacion testConversacion = conversacionList.get(conversacionList.size() - 1);
        assertThat(testConversacion.getUsuario1()).isEqualTo(DEFAULT_USUARIO_1);
        assertThat(testConversacion.getUsuario2()).isEqualTo(DEFAULT_USUARIO_2);
        assertThat(testConversacion.getFechaDeConversacion()).isEqualTo(DEFAULT_FECHA_DE_CONVERSACION);
    }

    @Test
    @Transactional
    public void createConversacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversacionRepository.findAll().size();

        // Create the Conversacion with an existing ID
        conversacion.setId(1L);
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(conversacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsuario1IsRequired() throws Exception {
        int databaseSizeBeforeTest = conversacionRepository.findAll().size();
        // set the field null
        conversacion.setUsuario1(null);

        // Create the Conversacion, which fails.
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(conversacion);

        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isBadRequest());

        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsuario2IsRequired() throws Exception {
        int databaseSizeBeforeTest = conversacionRepository.findAll().size();
        // set the field null
        conversacion.setUsuario2(null);

        // Create the Conversacion, which fails.
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(conversacion);

        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isBadRequest());

        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaDeConversacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversacionRepository.findAll().size();
        // set the field null
        conversacion.setFechaDeConversacion(null);

        // Create the Conversacion, which fails.
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(conversacion);

        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isBadRequest());

        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConversacions() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        // Get all the conversacionList
        restConversacionMockMvc.perform(get("/api/conversacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuario1").value(hasItem(DEFAULT_USUARIO_1)))
            .andExpect(jsonPath("$.[*].usuario2").value(hasItem(DEFAULT_USUARIO_2)))
            .andExpect(jsonPath("$.[*].fechaDeConversacion").value(hasItem(DEFAULT_FECHA_DE_CONVERSACION.toString())));
    }
    
    @Test
    @Transactional
    public void getConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        // Get the conversacion
        restConversacionMockMvc.perform(get("/api/conversacions/{id}", conversacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversacion.getId().intValue()))
            .andExpect(jsonPath("$.usuario1").value(DEFAULT_USUARIO_1))
            .andExpect(jsonPath("$.usuario2").value(DEFAULT_USUARIO_2))
            .andExpect(jsonPath("$.fechaDeConversacion").value(DEFAULT_FECHA_DE_CONVERSACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConversacion() throws Exception {
        // Get the conversacion
        restConversacionMockMvc.perform(get("/api/conversacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        int databaseSizeBeforeUpdate = conversacionRepository.findAll().size();

        // Update the conversacion
        Conversacion updatedConversacion = conversacionRepository.findById(conversacion.getId()).get();
        // Disconnect from session so that the updates on updatedConversacion are not directly saved in db
        em.detach(updatedConversacion);
        updatedConversacion
            .usuario1(UPDATED_USUARIO_1)
            .usuario2(UPDATED_USUARIO_2)
            .fechaDeConversacion(UPDATED_FECHA_DE_CONVERSACION);
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(updatedConversacion);

        restConversacionMockMvc.perform(put("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isOk());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeUpdate);
        Conversacion testConversacion = conversacionList.get(conversacionList.size() - 1);
        assertThat(testConversacion.getUsuario1()).isEqualTo(UPDATED_USUARIO_1);
        assertThat(testConversacion.getUsuario2()).isEqualTo(UPDATED_USUARIO_2);
        assertThat(testConversacion.getFechaDeConversacion()).isEqualTo(UPDATED_FECHA_DE_CONVERSACION);
    }

    @Test
    @Transactional
    public void updateNonExistingConversacion() throws Exception {
        int databaseSizeBeforeUpdate = conversacionRepository.findAll().size();

        // Create the Conversacion
        ConversacionDTO conversacionDTO = conversacionMapper.toDto(conversacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversacionMockMvc.perform(put("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        int databaseSizeBeforeDelete = conversacionRepository.findAll().size();

        // Delete the conversacion
        restConversacionMockMvc.perform(delete("/api/conversacions/{id}", conversacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conversacion.class);
        Conversacion conversacion1 = new Conversacion();
        conversacion1.setId(1L);
        Conversacion conversacion2 = new Conversacion();
        conversacion2.setId(conversacion1.getId());
        assertThat(conversacion1).isEqualTo(conversacion2);
        conversacion2.setId(2L);
        assertThat(conversacion1).isNotEqualTo(conversacion2);
        conversacion1.setId(null);
        assertThat(conversacion1).isNotEqualTo(conversacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversacionDTO.class);
        ConversacionDTO conversacionDTO1 = new ConversacionDTO();
        conversacionDTO1.setId(1L);
        ConversacionDTO conversacionDTO2 = new ConversacionDTO();
        assertThat(conversacionDTO1).isNotEqualTo(conversacionDTO2);
        conversacionDTO2.setId(conversacionDTO1.getId());
        assertThat(conversacionDTO1).isEqualTo(conversacionDTO2);
        conversacionDTO2.setId(2L);
        assertThat(conversacionDTO1).isNotEqualTo(conversacionDTO2);
        conversacionDTO1.setId(null);
        assertThat(conversacionDTO1).isNotEqualTo(conversacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conversacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conversacionMapper.fromId(null)).isNull();
    }
}
