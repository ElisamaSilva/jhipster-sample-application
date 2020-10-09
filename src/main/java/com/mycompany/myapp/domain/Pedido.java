package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.StatusdoPedido;

import com.mycompany.myapp.domain.enumeration.FormadePagamento;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
    private StatusdoPedido statusPedido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento", nullable = false)
    private FormadePagamento pagamento;

    @Column(name = "data_do_cadastro")
    private LocalDate dataDoCadastro;

    @Column(name = "lista_de_produtos")
    private String listaDeProdutos;

    @Column(name = "preco_total")
    private Float precoTotal;

    @Column(name = "valor_para_troco")
    private Float valorParaTroco;

    @Column(name = "entregador_alocado")
    private Boolean entregadorAlocado;

    @Column(name = "cliente_pediu_pedido")
    private Boolean clientePediuPedido;

    @Column(name = "observacoes_de_entrega")
    private String observacoesDeEntrega;

    @OneToOne
    @JoinColumn(unique = true)
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties(value = "pedidos", allowSetters = true)
    private Entregador entregador;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusdoPedido getStatusPedido() {
        return statusPedido;
    }

    public Pedido statusPedido(StatusdoPedido statusPedido) {
        this.statusPedido = statusPedido;
        return this;
    }

    public void setStatusPedido(StatusdoPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public FormadePagamento getPagamento() {
        return pagamento;
    }

    public Pedido pagamento(FormadePagamento pagamento) {
        this.pagamento = pagamento;
        return this;
    }

    public void setPagamento(FormadePagamento pagamento) {
        this.pagamento = pagamento;
    }

    public LocalDate getDataDoCadastro() {
        return dataDoCadastro;
    }

    public Pedido dataDoCadastro(LocalDate dataDoCadastro) {
        this.dataDoCadastro = dataDoCadastro;
        return this;
    }

    public void setDataDoCadastro(LocalDate dataDoCadastro) {
        this.dataDoCadastro = dataDoCadastro;
    }

    public String getListaDeProdutos() {
        return listaDeProdutos;
    }

    public Pedido listaDeProdutos(String listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
        return this;
    }

    public void setListaDeProdutos(String listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    public Float getPrecoTotal() {
        return precoTotal;
    }

    public Pedido precoTotal(Float precoTotal) {
        this.precoTotal = precoTotal;
        return this;
    }

    public void setPrecoTotal(Float precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Float getValorParaTroco() {
        return valorParaTroco;
    }

    public Pedido valorParaTroco(Float valorParaTroco) {
        this.valorParaTroco = valorParaTroco;
        return this;
    }

    public void setValorParaTroco(Float valorParaTroco) {
        this.valorParaTroco = valorParaTroco;
    }

    public Boolean isEntregadorAlocado() {
        return entregadorAlocado;
    }

    public Pedido entregadorAlocado(Boolean entregadorAlocado) {
        this.entregadorAlocado = entregadorAlocado;
        return this;
    }

    public void setEntregadorAlocado(Boolean entregadorAlocado) {
        this.entregadorAlocado = entregadorAlocado;
    }

    public Boolean isClientePediuPedido() {
        return clientePediuPedido;
    }

    public Pedido clientePediuPedido(Boolean clientePediuPedido) {
        this.clientePediuPedido = clientePediuPedido;
        return this;
    }

    public void setClientePediuPedido(Boolean clientePediuPedido) {
        this.clientePediuPedido = clientePediuPedido;
    }

    public String getObservacoesDeEntrega() {
        return observacoesDeEntrega;
    }

    public Pedido observacoesDeEntrega(String observacoesDeEntrega) {
        this.observacoesDeEntrega = observacoesDeEntrega;
        return this;
    }

    public void setObservacoesDeEntrega(String observacoesDeEntrega) {
        this.observacoesDeEntrega = observacoesDeEntrega;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public Pedido entregador(Entregador entregador) {
        this.entregador = entregador;
        return this;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", statusPedido='" + getStatusPedido() + "'" +
            ", pagamento='" + getPagamento() + "'" +
            ", dataDoCadastro='" + getDataDoCadastro() + "'" +
            ", listaDeProdutos='" + getListaDeProdutos() + "'" +
            ", precoTotal=" + getPrecoTotal() +
            ", valorParaTroco=" + getValorParaTroco() +
            ", entregadorAlocado='" + isEntregadorAlocado() + "'" +
            ", clientePediuPedido='" + isClientePediuPedido() + "'" +
            ", observacoesDeEntrega='" + getObservacoesDeEntrega() + "'" +
            "}";
    }
}
