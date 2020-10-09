package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SistemaDeliveryApp;
import com.mycompany.myapp.domain.Produto;
import com.mycompany.myapp.repository.ProdutoRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.EstadoProduto;
/**
 * Integration tests for the {@link ProdutoResource} REST controller.
 */
@SpringBootTest(classes = SistemaDeliveryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProdutoResourceIT {

    private static final EstadoProduto DEFAULT_PRODUTO = EstadoProduto.DISPONIVEL;
    private static final EstadoProduto UPDATED_PRODUTO = EstadoProduto.INDISPONIVEL;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Float DEFAULT_PRECO = 1F;
    private static final Float UPDATED_PRECO = 2F;

    private static final String DEFAULT_LINK_PARA_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_LINK_PARA_FOTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DE_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TOTAL_EM_ESTOQUE = 1F;
    private static final Float UPDATED_TOTAL_EM_ESTOQUE = 2F;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdutoMockMvc;

    private Produto produto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .produto(DEFAULT_PRODUTO)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .preco(DEFAULT_PRECO)
            .linkParaFoto(DEFAULT_LINK_PARA_FOTO)
            .dataDeCadastro(DEFAULT_DATA_DE_CADASTRO)
            .totalEmEstoque(DEFAULT_TOTAL_EM_ESTOQUE);
        return produto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createUpdatedEntity(EntityManager em) {
        Produto produto = new Produto()
            .produto(UPDATED_PRODUTO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .linkParaFoto(UPDATED_LINK_PARA_FOTO)
            .dataDeCadastro(UPDATED_DATA_DE_CADASTRO)
            .totalEmEstoque(UPDATED_TOTAL_EM_ESTOQUE);
        return produto;
    }

    @BeforeEach
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();
        // Create the Produto
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getProduto()).isEqualTo(DEFAULT_PRODUTO);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualTo(DEFAULT_PRECO);
        assertThat(testProduto.getLinkParaFoto()).isEqualTo(DEFAULT_LINK_PARA_FOTO);
        assertThat(testProduto.getDataDeCadastro()).isEqualTo(DEFAULT_DATA_DE_CADASTRO);
        assertThat(testProduto.getTotalEmEstoque()).isEqualTo(DEFAULT_TOTAL_EM_ESTOQUE);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setProduto(null);

        // Create the Produto, which fails.


        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.


        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].produto").value(hasItem(DEFAULT_PRODUTO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.doubleValue())))
            .andExpect(jsonPath("$.[*].linkParaFoto").value(hasItem(DEFAULT_LINK_PARA_FOTO)))
            .andExpect(jsonPath("$.[*].dataDeCadastro").value(hasItem(DEFAULT_DATA_DE_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].totalEmEstoque").value(hasItem(DEFAULT_TOTAL_EM_ESTOQUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.produto").value(DEFAULT_PRODUTO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.doubleValue()))
            .andExpect(jsonPath("$.linkParaFoto").value(DEFAULT_LINK_PARA_FOTO))
            .andExpect(jsonPath("$.dataDeCadastro").value(DEFAULT_DATA_DE_CADASTRO.toString()))
            .andExpect(jsonPath("$.totalEmEstoque").value(DEFAULT_TOTAL_EM_ESTOQUE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).get();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .produto(UPDATED_PRODUTO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .linkParaFoto(UPDATED_LINK_PARA_FOTO)
            .dataDeCadastro(UPDATED_DATA_DE_CADASTRO)
            .totalEmEstoque(UPDATED_TOTAL_EM_ESTOQUE);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduto)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getProduto()).isEqualTo(UPDATED_PRODUTO);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testProduto.getLinkParaFoto()).isEqualTo(UPDATED_LINK_PARA_FOTO);
        assertThat(testProduto.getDataDeCadastro()).isEqualTo(UPDATED_DATA_DE_CADASTRO);
        assertThat(testProduto.getTotalEmEstoque()).isEqualTo(UPDATED_TOTAL_EM_ESTOQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Delete the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
