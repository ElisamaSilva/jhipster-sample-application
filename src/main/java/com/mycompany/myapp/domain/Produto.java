package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.EstadoProduto;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "produto", nullable = false)
    private EstadoProduto produto;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Float preco;

    @Column(name = "link_para_foto")
    private String linkParaFoto;

    @Column(name = "data_de_cadastro")
    private LocalDate dataDeCadastro;

    @Column(name = "total_em_estoque")
    private Float totalEmEstoque;

    @ManyToOne
    @JsonIgnoreProperties(value = "produtos", allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoProduto getProduto() {
        return produto;
    }

    public Produto produto(EstadoProduto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(EstadoProduto produto) {
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPreco() {
        return preco;
    }

    public Produto preco(Float preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getLinkParaFoto() {
        return linkParaFoto;
    }

    public Produto linkParaFoto(String linkParaFoto) {
        this.linkParaFoto = linkParaFoto;
        return this;
    }

    public void setLinkParaFoto(String linkParaFoto) {
        this.linkParaFoto = linkParaFoto;
    }

    public LocalDate getDataDeCadastro() {
        return dataDeCadastro;
    }

    public Produto dataDeCadastro(LocalDate dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
        return this;
    }

    public void setDataDeCadastro(LocalDate dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public Float getTotalEmEstoque() {
        return totalEmEstoque;
    }

    public Produto totalEmEstoque(Float totalEmEstoque) {
        this.totalEmEstoque = totalEmEstoque;
        return this;
    }

    public void setTotalEmEstoque(Float totalEmEstoque) {
        this.totalEmEstoque = totalEmEstoque;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Produto cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", produto='" + getProduto() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", preco=" + getPreco() +
            ", linkParaFoto='" + getLinkParaFoto() + "'" +
            ", dataDeCadastro='" + getDataDeCadastro() + "'" +
            ", totalEmEstoque=" + getTotalEmEstoque() +
            "}";
    }
}
