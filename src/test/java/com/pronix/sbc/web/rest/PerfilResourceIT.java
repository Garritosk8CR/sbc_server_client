package com.pronix.sbc.web.rest;

import com.pronix.sbc.SbcAppApp;
import com.pronix.sbc.domain.Perfil;
import com.pronix.sbc.repository.PerfilRepository;
import com.pronix.sbc.service.PerfilService;
import com.pronix.sbc.service.dto.PerfilDTO;
import com.pronix.sbc.service.mapper.PerfilMapper;
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
 * Integration tests for the {@link PerfilResource} REST controller.
 */
@SpringBootTest(classes = SbcAppApp.class)
public class PerfilResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_EDAD = "AAAAAAAAAA";
    private static final String UPDATED_EDAD = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_CIVIL = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_CIVIL = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_FIJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_FIJO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CEDULA = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO = "BBBBBBBBBB";

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilMapper perfilMapper;

    @Autowired
    private PerfilService perfilService;

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

    private MockMvc restPerfilMockMvc;

    private Perfil perfil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerfilResource perfilResource = new PerfilResource(perfilService);
        this.restPerfilMockMvc = MockMvcBuilders.standaloneSetup(perfilResource)
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
    public static Perfil createEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .edad(DEFAULT_EDAD)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .sexo(DEFAULT_SEXO)
            .telefonoCelular(DEFAULT_TELEFONO_CELULAR)
            .telefonoFijo(DEFAULT_TELEFONO_FIJO)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .direccion(DEFAULT_DIRECCION)
            .cedula(DEFAULT_CEDULA)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .fechaSalida(DEFAULT_FECHA_SALIDA)
            .foto(DEFAULT_FOTO);
        return perfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfil createUpdatedEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .edad(UPDATED_EDAD)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .telefonoCelular(UPDATED_TELEFONO_CELULAR)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .direccion(UPDATED_DIRECCION)
            .cedula(UPDATED_CEDULA)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .foto(UPDATED_FOTO);
        return perfil;
    }

    @BeforeEach
    public void initTest() {
        perfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfil() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isCreated());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate + 1);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPerfil.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testPerfil.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testPerfil.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testPerfil.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testPerfil.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPerfil.getTelefonoCelular()).isEqualTo(DEFAULT_TELEFONO_CELULAR);
        assertThat(testPerfil.getTelefonoFijo()).isEqualTo(DEFAULT_TELEFONO_FIJO);
        assertThat(testPerfil.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testPerfil.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPerfil.getCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testPerfil.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testPerfil.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testPerfil.getFoto()).isEqualTo(DEFAULT_FOTO);
    }

    @Test
    @Transactional
    public void createPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil with an existing ID
        perfil.setId(1L);
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setNombre(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setPrimerApellido(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSegundoApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setSegundoApellido(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEdadIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setEdad(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoCivilIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setEstadoCivil(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setSexo(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setTelefonoCelular(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoFijoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setTelefonoFijo(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoElectronicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setCorreoElectronico(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setDireccion(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCedulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setCedula(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIngresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setFechaIngreso(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setFoto(null);

        // Create the Perfil, which fails.
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerfils() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].telefonoCelular").value(hasItem(DEFAULT_TELEFONO_CELULAR)))
            .andExpect(jsonPath("$.[*].telefonoFijo").value(hasItem(DEFAULT_TELEFONO_FIJO)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO)));
    }
    
    @Test
    @Transactional
    public void getPerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", perfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfil.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.telefonoCelular").value(DEFAULT_TELEFONO_CELULAR))
            .andExpect(jsonPath("$.telefonoFijo").value(DEFAULT_TELEFONO_FIJO))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO));
    }

    @Test
    @Transactional
    public void getNonExistingPerfil() throws Exception {
        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Update the perfil
        Perfil updatedPerfil = perfilRepository.findById(perfil.getId()).get();
        // Disconnect from session so that the updates on updatedPerfil are not directly saved in db
        em.detach(updatedPerfil);
        updatedPerfil
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .edad(UPDATED_EDAD)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .telefonoCelular(UPDATED_TELEFONO_CELULAR)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .direccion(UPDATED_DIRECCION)
            .cedula(UPDATED_CEDULA)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .foto(UPDATED_FOTO);
        PerfilDTO perfilDTO = perfilMapper.toDto(updatedPerfil);

        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isOk());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPerfil.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPerfil.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPerfil.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testPerfil.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testPerfil.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPerfil.getTelefonoCelular()).isEqualTo(UPDATED_TELEFONO_CELULAR);
        assertThat(testPerfil.getTelefonoFijo()).isEqualTo(UPDATED_TELEFONO_FIJO);
        assertThat(testPerfil.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testPerfil.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPerfil.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testPerfil.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testPerfil.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testPerfil.getFoto()).isEqualTo(UPDATED_FOTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfil() throws Exception {
        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Create the Perfil
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        int databaseSizeBeforeDelete = perfilRepository.findAll().size();

        // Delete the perfil
        restPerfilMockMvc.perform(delete("/api/perfils/{id}", perfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perfil.class);
        Perfil perfil1 = new Perfil();
        perfil1.setId(1L);
        Perfil perfil2 = new Perfil();
        perfil2.setId(perfil1.getId());
        assertThat(perfil1).isEqualTo(perfil2);
        perfil2.setId(2L);
        assertThat(perfil1).isNotEqualTo(perfil2);
        perfil1.setId(null);
        assertThat(perfil1).isNotEqualTo(perfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilDTO.class);
        PerfilDTO perfilDTO1 = new PerfilDTO();
        perfilDTO1.setId(1L);
        PerfilDTO perfilDTO2 = new PerfilDTO();
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
        perfilDTO2.setId(perfilDTO1.getId());
        assertThat(perfilDTO1).isEqualTo(perfilDTO2);
        perfilDTO2.setId(2L);
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
        perfilDTO1.setId(null);
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(perfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(perfilMapper.fromId(null)).isNull();
    }
}
