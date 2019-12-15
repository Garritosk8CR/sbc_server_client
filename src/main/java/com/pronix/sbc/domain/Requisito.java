package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Requisito entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "requisito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Requisito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @ManyToMany(mappedBy = "requerimientos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<PuestoDeTrabajo> puestoDeTrabajos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Requisito nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Requisito descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public Requisito tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<PuestoDeTrabajo> getPuestoDeTrabajos() {
        return puestoDeTrabajos;
    }

    public Requisito puestoDeTrabajos(Set<PuestoDeTrabajo> puestoDeTrabajos) {
        this.puestoDeTrabajos = puestoDeTrabajos;
        return this;
    }

    public Requisito addPuestoDeTrabajo(PuestoDeTrabajo puestoDeTrabajo) {
        this.puestoDeTrabajos.add(puestoDeTrabajo);
        puestoDeTrabajo.getRequerimientos().add(this);
        return this;
    }

    public Requisito removePuestoDeTrabajo(PuestoDeTrabajo puestoDeTrabajo) {
        this.puestoDeTrabajos.remove(puestoDeTrabajo);
        puestoDeTrabajo.getRequerimientos().remove(this);
        return this;
    }

    public void setPuestoDeTrabajos(Set<PuestoDeTrabajo> puestoDeTrabajos) {
        this.puestoDeTrabajos = puestoDeTrabajos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requisito)) {
            return false;
        }
        return id != null && id.equals(((Requisito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Requisito{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
