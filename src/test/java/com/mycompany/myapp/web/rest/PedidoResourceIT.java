package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SistemaDeliveryApp;
import com.mycompany.myapp.domain.Pedido;
import com.mycompany.myapp.repository.PedidoRepository;

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

import com.mycompany.myapp.domain.enumeration.StatusdoPedido;
import com.mycompany.myapp.domain.enumeration.FormadePagamento;
/**
 * Integration tests for the {@link PedidoResource} REST controller.
 */
@SpringBootTest(classes = SistemaDeliveryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PedidoResourceIT {

    private static final StatusdoPedido DEFAULT_STATUS_PEDIDO = StatusdoPedido.PEDIDO;
    private static final StatusdoPedido UPDATED_STATUS_PEDIDO = StatusdoPedido.EM_TRANSITO;

    private static final FormadePagamento DEFAULT_PAGAMENTO = FormadePagamento.DINHEIRO;
    private static final FormadePagamento UPDATED_PAGAMENTO = FormadePagamento.CARTAO;

    private static final LocalDate DEFAULT_DATA_DO_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DO_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LISTA_DE_PRODUTOS = "AAAAAAAAAA";
    private static final String UPDATED_LISTA_DE_PRODUTOS = "BBBBBBBBBB";

    private static final Float DEFAULT_PRECO_TOTAL = 1F;
    private static final Float UPDATED_PRECO_TOTAL = 2F;

    private static final Float DEFAULT_VALOR_PARA_TROCO = 1F;
    private static final Float UPDATED_VALOR_PARA_TROCO = 2F;

    private static final Boolean DEFAULT_ENTREGADOR_ALOCADO = false;
    private static final Boolean UPDATED_ENTREGADOR_ALOCADO = true;

    private static final Boolean DEFAULT_CLIENTE_PEDIU_PEDIDO = false;
    private static final Boolean UPDATED_CLIENTE_PEDIU_PEDIDO = true;

    private static final String DEFAULT_OBSERVACOES_DE_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES_DE_ENTREGA = "BBBBBBBBBB";

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .statusPedido(DEFAULT_STATUS_PEDIDO)
            .pagamento(DEFAULT_PAGAMENTO)
            .dataDoCadastro(DEFAULT_DATA_DO_CADASTRO)
            .listaDeProdutos(DEFAULT_LISTA_DE_PRODUTOS)
            .precoTotal(DEFAULT_PRECO_TOTAL)
            .valorParaTroco(DEFAULT_VALOR_PARA_TROCO)
            .entregadorAlocado(DEFAULT_ENTREGADOR_ALOCADO)
            .clientePediuPedido(DEFAULT_CLIENTE_PEDIU_PEDIDO)
            .observacoesDeEntrega(DEFAULT_OBSERVACOES_DE_ENTREGA);
        return pedido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createUpdatedEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .statusPedido(UPDATED_STATUS_PEDIDO)
            .pagamento(UPDATED_PAGAMENTO)
            .dataDoCadastro(UPDATED_DATA_DO_CADASTRO)
            .listaDeProdutos(UPDATED_LISTA_DE_PRODUTOS)
            .precoTotal(UPDATED_PRECO_TOTAL)
            .valorParaTroco(UPDATED_VALOR_PARA_TROCO)
            .entregadorAlocado(UPDATED_ENTREGADOR_ALOCADO)
            .clientePediuPedido(UPDATED_CLIENTE_PEDIU_PEDIDO)
            .observacoesDeEntrega(UPDATED_OBSERVACOES_DE_ENTREGA);
        return pedido;
    }

    @BeforeEach
    public void initTest() {
        pedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();
        // Create the Pedido
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getStatusPedido()).isEqualTo(DEFAULT_STATUS_PEDIDO);
        assertThat(testPedido.getPagamento()).isEqualTo(DEFAULT_PAGAMENTO);
        assertThat(testPedido.getDataDoCadastro()).isEqualTo(DEFAULT_DATA_DO_CADASTRO);
        assertThat(testPedido.getListaDeProdutos()).isEqualTo(DEFAULT_LISTA_DE_PRODUTOS);
        assertThat(testPedido.getPrecoTotal()).isEqualTo(DEFAULT_PRECO_TOTAL);
        assertThat(testPedido.getValorParaTroco()).isEqualTo(DEFAULT_VALOR_PARA_TROCO);
        assertThat(testPedido.isEntregadorAlocado()).isEqualTo(DEFAULT_ENTREGADOR_ALOCADO);
        assertThat(testPedido.isClientePediuPedido()).isEqualTo(DEFAULT_CLIENTE_PEDIU_PEDIDO);
        assertThat(testPedido.getObservacoesDeEntrega()).isEqualTo(DEFAULT_OBSERVACOES_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void createPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido with an existing ID
        pedido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusPedidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setStatusPedido(null);

        // Create the Pedido, which fails.


        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPagamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setPagamento(null);

        // Create the Pedido, which fails.


        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusPedido").value(hasItem(DEFAULT_STATUS_PEDIDO.toString())))
            .andExpect(jsonPath("$.[*].pagamento").value(hasItem(DEFAULT_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataDoCadastro").value(hasItem(DEFAULT_DATA_DO_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].listaDeProdutos").value(hasItem(DEFAULT_LISTA_DE_PRODUTOS)))
            .andExpect(jsonPath("$.[*].precoTotal").value(hasItem(DEFAULT_PRECO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].valorParaTroco").value(hasItem(DEFAULT_VALOR_PARA_TROCO.doubleValue())))
            .andExpect(jsonPath("$.[*].entregadorAlocado").value(hasItem(DEFAULT_ENTREGADOR_ALOCADO.booleanValue())))
            .andExpect(jsonPath("$.[*].clientePediuPedido").value(hasItem(DEFAULT_CLIENTE_PEDIU_PEDIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacoesDeEntrega").value(hasItem(DEFAULT_OBSERVACOES_DE_ENTREGA)));
    }
    
    @Test
    @Transactional
    public void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId().intValue()))
            .andExpect(jsonPath("$.statusPedido").value(DEFAULT_STATUS_PEDIDO.toString()))
            .andExpect(jsonPath("$.pagamento").value(DEFAULT_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.dataDoCadastro").value(DEFAULT_DATA_DO_CADASTRO.toString()))
            .andExpect(jsonPath("$.listaDeProdutos").value(DEFAULT_LISTA_DE_PRODUTOS))
            .andExpect(jsonPath("$.precoTotal").value(DEFAULT_PRECO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.valorParaTroco").value(DEFAULT_VALOR_PARA_TROCO.doubleValue()))
            .andExpect(jsonPath("$.entregadorAlocado").value(DEFAULT_ENTREGADOR_ALOCADO.booleanValue()))
            .andExpect(jsonPath("$.clientePediuPedido").value(DEFAULT_CLIENTE_PEDIU_PEDIDO.booleanValue()))
            .andExpect(jsonPath("$.observacoesDeEntrega").value(DEFAULT_OBSERVACOES_DE_ENTREGA));
    }
    @Test
    @Transactional
    public void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        // Disconnect from session so that the updates on updatedPedido are not directly saved in db
        em.detach(updatedPedido);
        updatedPedido
            .statusPedido(UPDATED_STATUS_PEDIDO)
            .pagamento(UPDATED_PAGAMENTO)
            .dataDoCadastro(UPDATED_DATA_DO_CADASTRO)
            .listaDeProdutos(UPDATED_LISTA_DE_PRODUTOS)
            .precoTotal(UPDATED_PRECO_TOTAL)
            .valorParaTroco(UPDATED_VALOR_PARA_TROCO)
            .entregadorAlocado(UPDATED_ENTREGADOR_ALOCADO)
            .clientePediuPedido(UPDATED_CLIENTE_PEDIU_PEDIDO)
            .observacoesDeEntrega(UPDATED_OBSERVACOES_DE_ENTREGA);

        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPedido)))
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getStatusPedido()).isEqualTo(UPDATED_STATUS_PEDIDO);
        assertThat(testPedido.getPagamento()).isEqualTo(UPDATED_PAGAMENTO);
        assertThat(testPedido.getDataDoCadastro()).isEqualTo(UPDATED_DATA_DO_CADASTRO);
        assertThat(testPedido.getListaDeProdutos()).isEqualTo(UPDATED_LISTA_DE_PRODUTOS);
        assertThat(testPedido.getPrecoTotal()).isEqualTo(UPDATED_PRECO_TOTAL);
        assertThat(testPedido.getValorParaTroco()).isEqualTo(UPDATED_VALOR_PARA_TROCO);
        assertThat(testPedido.isEntregadorAlocado()).isEqualTo(UPDATED_ENTREGADOR_ALOCADO);
        assertThat(testPedido.isClientePediuPedido()).isEqualTo(UPDATED_CLIENTE_PEDIU_PEDIDO);
        assertThat(testPedido.getObservacoesDeEntrega()).isEqualTo(UPDATED_OBSERVACOES_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void updateNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Delete the pedido
        restPedidoMockMvc.perform(delete("/api/pedidos/{id}", pedido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
