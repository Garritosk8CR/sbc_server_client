package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.Mensaje;
import com.pronix.sbc.repository.MensajeRepository;
import com.pronix.sbc.service.MensajeService;
import com.pronix.sbc.service.dto.MensajeDTO;
import com.pronix.sbc.service.mapper.MensajeMapper;
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

import com.pronix.sbc.domain.enumeration.EstadoMensaje;
/**
 * Integration tests for the {@link MensajeResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class MensajeResourceIT {

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_EMISION = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_EMISION = "BBBBBBBBBB";

    private static final EstadoMensaje DEFAULT_ESTADO_MENSAJE = EstadoMensaje.LEIDO;
    private static final EstadoMensaje UPDATED_ESTADO_MENSAJE = EstadoMensaje.NO_LEIDO;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private MensajeMapper mensajeMapper;

    @Autowired
    private MensajeService mensajeService;

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

    private MockMvc restMensajeMockMvc;

    private Mensaje mensaje;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MensajeResource mensajeResource = new MensajeResource(mensajeService);
        this.restMensajeMockMvc = MockMvcBuilders.standaloneSetup(mensajeResource)
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
    public static Mensaje createEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .mensaje(DEFAULT_MENSAJE)
            .fechaEmision(DEFAULT_FECHA_EMISION)
            .estadoMensaje(DEFAULT_ESTADO_MENSAJE);
        return mensaje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createUpdatedEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .mensaje(UPDATED_MENSAJE)
            .fechaEmision(UPDATED_FECHA_EMISION)
            .estadoMensaje(UPDATED_ESTADO_MENSAJE);
        return mensaje;
    }

    @BeforeEach
    public void initTest() {
        mensaje = createEntity(em);
    }

    @Test
    @Transactional
    public void createMensaje() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensajeDTO)))
            .andExpect(status().isCreated());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate + 1);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
        assertThat(testMensaje.getFechaEmision()).isEqualTo(DEFAULT_FECHA_EMISION);
        assertThat(testMensaje.getEstadoMensaje()).isEqualTo(DEFAULT_ESTADO_MENSAJE);
    }

    @Test
    @Transactional
    public void createMensajeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje with an existing ID
        mensaje.setId(1L);
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMensajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajeRepository.findAll().size();
        // set the field null
        mensaje.setMensaje(null);

        // Create the Mensaje, which fails.
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaEmisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajeRepository.findAll().size();
        // set the field null
        mensaje.setFechaEmision(null);

        // Create the Mensaje, which fails.
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMensajes() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get all the mensajeList
        restMensajeMockMvc.perform(get("/api/mensajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].fechaEmision").value(hasItem(DEFAULT_FECHA_EMISION)))
            .andExpect(jsonPath("$.[*].estadoMensaje").value(hasItem(DEFAULT_ESTADO_MENSAJE.toString())));
    }
    
    @Test
    @Transactional
    public void getMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", mensaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mensaje.getId().intValue()))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE))
            .andExpect(jsonPath("$.fechaEmision").value(DEFAULT_FECHA_EMISION))
            .andExpect(jsonPath("$.estadoMensaje").value(DEFAULT_ESTADO_MENSAJE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMensaje() throws Exception {
        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Update the mensaje
        Mensaje updatedMensaje = mensajeRepository.findById(mensaje.getId()).get();
        // Disconnect from session so that the updates on updatedMensaje are not directly saved in db
        em.detach(updatedMensaje);
        updatedMensaje
            .mensaje(UPDATED_MENSAJE)
            .fechaEmision(UPDATED_FECHA_EMISION)
            .estadoMensaje(UPDATED_ESTADO_MENSAJE);
        MensajeDTO mensajeDTO = mensajeMapper.toDto(updatedMensaje);

        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensajeDTO)))
            .andExpect(status().isOk());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testMensaje.getFechaEmision()).isEqualTo(UPDATED_FECHA_EMISION);
        assertThat(testMensaje.getEstadoMensaje()).isEqualTo(UPDATED_ESTADO_MENSAJE);
    }

    @Test
    @Transactional
    public void updateNonExistingMensaje() throws Exception {
        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        int databaseSizeBeforeDelete = mensajeRepository.findAll().size();

        // Delete the mensaje
        restMensajeMockMvc.perform(delete("/api/mensajes/{id}", mensaje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mensaje.class);
        Mensaje mensaje1 = new Mensaje();
        mensaje1.setId(1L);
        Mensaje mensaje2 = new Mensaje();
        mensaje2.setId(mensaje1.getId());
        assertThat(mensaje1).isEqualTo(mensaje2);
        mensaje2.setId(2L);
        assertThat(mensaje1).isNotEqualTo(mensaje2);
        mensaje1.setId(null);
        assertThat(mensaje1).isNotEqualTo(mensaje2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensajeDTO.class);
        MensajeDTO mensajeDTO1 = new MensajeDTO();
        mensajeDTO1.setId(1L);
        MensajeDTO mensajeDTO2 = new MensajeDTO();
        assertThat(mensajeDTO1).isNotEqualTo(mensajeDTO2);
        mensajeDTO2.setId(mensajeDTO1.getId());
        assertThat(mensajeDTO1).isEqualTo(mensajeDTO2);
        mensajeDTO2.setId(2L);
        assertThat(mensajeDTO1).isNotEqualTo(mensajeDTO2);
        mensajeDTO1.setId(null);
        assertThat(mensajeDTO1).isNotEqualTo(mensajeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mensajeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mensajeMapper.fromId(null)).isNull();
    }
}
