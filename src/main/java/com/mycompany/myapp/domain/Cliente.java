package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column(name = "cep_de_endereco")
    private Integer cepDeEndereco;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Produto> produtos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "cliente_entregadores",
               joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "entregadores_id", referencedColumnName = "id"))
    private Set<Entregador> entregadores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Cliente telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public Cliente login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public Cliente senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getCepDeEndereco() {
        return cepDeEndereco;
    }

    public Cliente cepDeEndereco(Integer cepDeEndereco) {
        this.cepDeEndereco = cepDeEndereco;
        return this;
    }

    public void setCepDeEndereco(Integer cepDeEndereco) {
        this.cepDeEndereco = cepDeEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Cliente logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public Cliente bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public Cliente cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Cliente produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Cliente addProdutos(Produto produto) {
        this.produtos.add(produto);
        produto.setCliente(this);
        return this;
    }

    public Cliente removeProdutos(Produto produto) {
        this.produtos.remove(produto);
        produto.setCliente(null);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<Entregador> getEntregadores() {
        return entregadores;
    }

    public Cliente entregadores(Set<Entregador> entregadors) {
        this.entregadores = entregadors;
        return this;
    }

    public Cliente addEntregadores(Entregador entregador) {
        this.entregadores.add(entregador);
        entregador.getClientes().add(this);
        return this;
    }

    public Cliente removeEntregadores(Entregador entregador) {
        this.entregadores.remove(entregador);
        entregador.getClientes().remove(this);
        return this;
    }

    public void setEntregadores(Set<Entregador> entregadors) {
        this.entregadores = entregadors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", login='" + getLogin() + "'" +
            ", senha='" + getSenha() + "'" +
            ", cepDeEndereco=" + getCepDeEndereco() +
            ", logradouro='" + getLogradouro() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            "}";
    }
}
