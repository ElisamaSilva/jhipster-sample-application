package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Entregador.
 */
@Entity
@Table(name = "entregador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entregador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "placa_da_moto")
    private String placaDaMoto;

    @Column(name = "latitude_atual")
    private Double latitudeAtual;

    @Column(name = "longitude_atual")
    private Double longitudeAtual;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private Integer cpf;

    @Column(name = "link_para_foto")
    private String linkParaFoto;

    @OneToMany(mappedBy = "entregador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToMany(mappedBy = "entregadores")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Cliente> clientes = new HashSet<>();

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

    public Entregador nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPlacaDaMoto() {
        return placaDaMoto;
    }

    public Entregador placaDaMoto(String placaDaMoto) {
        this.placaDaMoto = placaDaMoto;
        return this;
    }

    public void setPlacaDaMoto(String placaDaMoto) {
        this.placaDaMoto = placaDaMoto;
    }

    public Double getLatitudeAtual() {
        return latitudeAtual;
    }

    public Entregador latitudeAtual(Double latitudeAtual) {
        this.latitudeAtual = latitudeAtual;
        return this;
    }

    public void setLatitudeAtual(Double latitudeAtual) {
        this.latitudeAtual = latitudeAtual;
    }

    public Double getLongitudeAtual() {
        return longitudeAtual;
    }

    public Entregador longitudeAtual(Double longitudeAtual) {
        this.longitudeAtual = longitudeAtual;
        return this;
    }

    public void setLongitudeAtual(Double longitudeAtual) {
        this.longitudeAtual = longitudeAtual;
    }

    public Integer getCpf() {
        return cpf;
    }

    public Entregador cpf(Integer cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getLinkParaFoto() {
        return linkParaFoto;
    }

    public Entregador linkParaFoto(String linkParaFoto) {
        this.linkParaFoto = linkParaFoto;
        return this;
    }

    public void setLinkParaFoto(String linkParaFoto) {
        this.linkParaFoto = linkParaFoto;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Entregador pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Entregador addPedidos(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEntregador(this);
        return this;
    }

    public Entregador removePedidos(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setEntregador(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Entregador clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Entregador addClientes(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.getEntregadores().add(this);
        return this;
    }

    public Entregador removeClientes(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.getEntregadores().remove(this);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entregador)) {
            return false;
        }
        return id != null && id.equals(((Entregador) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entregador{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", placaDaMoto='" + getPlacaDaMoto() + "'" +
            ", latitudeAtual=" + getLatitudeAtual() +
            ", longitudeAtual=" + getLongitudeAtual() +
            ", cpf=" + getCpf() +
            ", linkParaFoto='" + getLinkParaFoto() + "'" +
            "}";
    }
}
