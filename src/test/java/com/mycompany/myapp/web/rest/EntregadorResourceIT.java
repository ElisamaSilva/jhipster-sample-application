package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SistemaDeliveryApp;
import com.mycompany.myapp.domain.Entregador;
import com.mycompany.myapp.repository.EntregadorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EntregadorResource} REST controller.
 */
@SpringBootTest(classes = SistemaDeliveryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EntregadorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_PLACA_DA_MOTO = "AAAAAAAAAA";
    private static final String UPDATED_PLACA_DA_MOTO = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE_ATUAL = 1D;
    private static final Double UPDATED_LATITUDE_ATUAL = 2D;

    private static final Double DEFAULT_LONGITUDE_ATUAL = 1D;
    private static final Double UPDATED_LONGITUDE_ATUAL = 2D;

    private static final Integer DEFAULT_CPF = 1;
    private static final Integer UPDATED_CPF = 2;

    private static final String DEFAULT_LINK_PARA_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_LINK_PARA_FOTO = "BBBBBBBBBB";

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntregadorMockMvc;

    private Entregador entregador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entregador createEntity(EntityManager em) {
        Entregador entregador = new Entregador()
            .nome(DEFAULT_NOME)
            .placaDaMoto(DEFAULT_PLACA_DA_MOTO)
            .latitudeAtual(DEFAULT_LATITUDE_ATUAL)
            .longitudeAtual(DEFAULT_LONGITUDE_ATUAL)
            .cpf(DEFAULT_CPF)
            .linkParaFoto(DEFAULT_LINK_PARA_FOTO);
        return entregador;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entregador createUpdatedEntity(EntityManager em) {
        Entregador entregador = new Entregador()
            .nome(UPDATED_NOME)
            .placaDaMoto(UPDATED_PLACA_DA_MOTO)
            .latitudeAtual(UPDATED_LATITUDE_ATUAL)
            .longitudeAtual(UPDATED_LONGITUDE_ATUAL)
            .cpf(UPDATED_CPF)
            .linkParaFoto(UPDATED_LINK_PARA_FOTO);
        return entregador;
    }

    @BeforeEach
    public void initTest() {
        entregador = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntregador() throws Exception {
        int databaseSizeBeforeCreate = entregadorRepository.findAll().size();
        // Create the Entregador
        restEntregadorMockMvc.perform(post("/api/entregadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isCreated());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeCreate + 1);
        Entregador testEntregador = entregadorList.get(entregadorList.size() - 1);
        assertThat(testEntregador.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEntregador.getPlacaDaMoto()).isEqualTo(DEFAULT_PLACA_DA_MOTO);
        assertThat(testEntregador.getLatitudeAtual()).isEqualTo(DEFAULT_LATITUDE_ATUAL);
        assertThat(testEntregador.getLongitudeAtual()).isEqualTo(DEFAULT_LONGITUDE_ATUAL);
        assertThat(testEntregador.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testEntregador.getLinkParaFoto()).isEqualTo(DEFAULT_LINK_PARA_FOTO);
    }

    @Test
    @Transactional
    public void createEntregadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entregadorRepository.findAll().size();

        // Create the Entregador with an existing ID
        entregador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntregadorMockMvc.perform(post("/api/entregadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = entregadorRepository.findAll().size();
        // set the field null
        entregador.setNome(null);

        // Create the Entregador, which fails.


        restEntregadorMockMvc.perform(post("/api/entregadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isBadRequest());

        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = entregadorRepository.findAll().size();
        // set the field null
        entregador.setCpf(null);

        // Create the Entregador, which fails.


        restEntregadorMockMvc.perform(post("/api/entregadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isBadRequest());

        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntregadors() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        // Get all the entregadorList
        restEntregadorMockMvc.perform(get("/api/entregadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entregador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].placaDaMoto").value(hasItem(DEFAULT_PLACA_DA_MOTO)))
            .andExpect(jsonPath("$.[*].latitudeAtual").value(hasItem(DEFAULT_LATITUDE_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].longitudeAtual").value(hasItem(DEFAULT_LONGITUDE_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].linkParaFoto").value(hasItem(DEFAULT_LINK_PARA_FOTO)));
    }
    
    @Test
    @Transactional
    public void getEntregador() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        // Get the entregador
        restEntregadorMockMvc.perform(get("/api/entregadors/{id}", entregador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entregador.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.placaDaMoto").value(DEFAULT_PLACA_DA_MOTO))
            .andExpect(jsonPath("$.latitudeAtual").value(DEFAULT_LATITUDE_ATUAL.doubleValue()))
            .andExpect(jsonPath("$.longitudeAtual").value(DEFAULT_LONGITUDE_ATUAL.doubleValue()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.linkParaFoto").value(DEFAULT_LINK_PARA_FOTO));
    }
    @Test
    @Transactional
    public void getNonExistingEntregador() throws Exception {
        // Get the entregador
        restEntregadorMockMvc.perform(get("/api/entregadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntregador() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();

        // Update the entregador
        Entregador updatedEntregador = entregadorRepository.findById(entregador.getId()).get();
        // Disconnect from session so that the updates on updatedEntregador are not directly saved in db
        em.detach(updatedEntregador);
        updatedEntregador
            .nome(UPDATED_NOME)
            .placaDaMoto(UPDATED_PLACA_DA_MOTO)
            .latitudeAtual(UPDATED_LATITUDE_ATUAL)
            .longitudeAtual(UPDATED_LONGITUDE_ATUAL)
            .cpf(UPDATED_CPF)
            .linkParaFoto(UPDATED_LINK_PARA_FOTO);

        restEntregadorMockMvc.perform(put("/api/entregadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntregador)))
            .andExpect(status().isOk());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
        Entregador testEntregador = entregadorList.get(entregadorList.size() - 1);
        assertThat(testEntregador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEntregador.getPlacaDaMoto()).isEqualTo(UPDATED_PLACA_DA_MOTO);
        assertThat(testEntregador.getLatitudeAtual()).isEqualTo(UPDATED_LATITUDE_ATUAL);
        assertThat(testEntregador.getLongitudeAtual()).isEqualTo(UPDATED_LONGITUDE_ATUAL);
        assertThat(testEntregador.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testEntregador.getLinkParaFoto()).isEqualTo(UPDATED_LINK_PARA_FOTO);
    }

    @Test
    @Transactional
    public void updateNonExistingEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntregadorMockMvc.perform(put("/api/entregadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntregador() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        int databaseSizeBeforeDelete = entregadorRepository.findAll().size();

        // Delete the entregador
        restEntregadorMockMvc.perform(delete("/api/entregadors/{id}", entregador.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
