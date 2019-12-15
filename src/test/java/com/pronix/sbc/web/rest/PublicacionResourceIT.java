package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.Publicacion;
import com.pronix.sbc.repository.PublicacionRepository;
import com.pronix.sbc.service.PublicacionService;
import com.pronix.sbc.service.dto.PublicacionDTO;
import com.pronix.sbc.service.mapper.PublicacionMapper;
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

import com.pronix.sbc.domain.enumeration.TipoPublicacion;
/**
 * Integration tests for the {@link PublicacionResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class PublicacionResourceIT {

    private static final LocalDate DEFAULT_FECHA_PUBLICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PUBLICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final TipoPublicacion DEFAULT_TIPO_PUBLICACION = TipoPublicacion.ESPECIAL;
    private static final TipoPublicacion UPDATED_TIPO_PUBLICACION = TipoPublicacion.NO_ESPECIAL;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    private PublicacionService publicacionService;

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

    private MockMvc restPublicacionMockMvc;

    private Publicacion publicacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacionResource publicacionResource = new PublicacionResource(publicacionService);
        this.restPublicacionMockMvc = MockMvcBuilders.standaloneSetup(publicacionResource)
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
    public static Publicacion createEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .fechaPublicacion(DEFAULT_FECHA_PUBLICACION)
            .contenido(DEFAULT_CONTENIDO)
            .tipoPublicacion(DEFAULT_TIPO_PUBLICACION);
        return publicacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createUpdatedEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .contenido(UPDATED_CONTENIDO)
            .tipoPublicacion(UPDATED_TIPO_PUBLICACION);
        return publicacion;
    }

    @BeforeEach
    public void initTest() {
        publicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacion() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate + 1);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getFechaPublicacion()).isEqualTo(DEFAULT_FECHA_PUBLICACION);
        assertThat(testPublicacion.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testPublicacion.getTipoPublicacion()).isEqualTo(DEFAULT_TIPO_PUBLICACION);
    }

    @Test
    @Transactional
    public void createPublicacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion with an existing ID
        publicacion.setId(1L);
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContenidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacionRepository.findAll().size();
        // set the field null
        publicacion.setContenido(null);

        // Create the Publicacion, which fails.
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isBadRequest());

        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicacions() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO)))
            .andExpect(jsonPath("$.[*].tipoPublicacion").value(hasItem(DEFAULT_TIPO_PUBLICACION.toString())));
    }
    
    @Test
    @Transactional
    public void getPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", publicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacion.getId().intValue()))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO))
            .andExpect(jsonPath("$.tipoPublicacion").value(DEFAULT_TIPO_PUBLICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacion() throws Exception {
        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion
        Publicacion updatedPublicacion = publicacionRepository.findById(publicacion.getId()).get();
        // Disconnect from session so that the updates on updatedPublicacion are not directly saved in db
        em.detach(updatedPublicacion);
        updatedPublicacion
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .contenido(UPDATED_CONTENIDO)
            .tipoPublicacion(UPDATED_TIPO_PUBLICACION);
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(updatedPublicacion);

        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getFechaPublicacion()).isEqualTo(UPDATED_FECHA_PUBLICACION);
        assertThat(testPublicacion.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testPublicacion.getTipoPublicacion()).isEqualTo(UPDATED_TIPO_PUBLICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeDelete = publicacionRepository.findAll().size();

        // Delete the publicacion
        restPublicacionMockMvc.perform(delete("/api/publicacions/{id}", publicacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publicacion.class);
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setId(1L);
        Publicacion publicacion2 = new Publicacion();
        publicacion2.setId(publicacion1.getId());
        assertThat(publicacion1).isEqualTo(publicacion2);
        publicacion2.setId(2L);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
        publicacion1.setId(null);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacionDTO.class);
        PublicacionDTO publicacionDTO1 = new PublicacionDTO();
        publicacionDTO1.setId(1L);
        PublicacionDTO publicacionDTO2 = new PublicacionDTO();
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
        publicacionDTO2.setId(publicacionDTO1.getId());
        assertThat(publicacionDTO1).isEqualTo(publicacionDTO2);
        publicacionDTO2.setId(2L);
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
        publicacionDTO1.setId(null);
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(publicacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(publicacionMapper.fromId(null)).isNull();
    }
}
