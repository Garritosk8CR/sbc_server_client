package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * PuestoDeTrabajo entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "puesto_de_trabajo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PuestoDeTrabajo implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("puestoDeTrabajos")
    private CarreraProfesional carreraProfesional;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "puesto_de_trabajo_requerimiento",
               joinColumns = @JoinColumn(name = "puesto_de_trabajo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "requerimiento_id", referencedColumnName = "id"))
    private Set<Requisito> requerimientos = new HashSet<>();

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

    public PuestoDeTrabajo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public PuestoDeTrabajo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CarreraProfesional getCarreraProfesional() {
        return carreraProfesional;
    }

    public PuestoDeTrabajo carreraProfesional(CarreraProfesional carreraProfesional) {
        this.carreraProfesional = carreraProfesional;
        return this;
    }

    public void setCarreraProfesional(CarreraProfesional carreraProfesional) {
        this.carreraProfesional = carreraProfesional;
    }

    public Set<Requisito> getRequerimientos() {
        return requerimientos;
    }

    public PuestoDeTrabajo requerimientos(Set<Requisito> requisitos) {
        this.requerimientos = requisitos;
        return this;
    }

    public PuestoDeTrabajo addRequerimiento(Requisito requisito) {
        this.requerimientos.add(requisito);
        requisito.getPuestoDeTrabajos().add(this);
        return this;
    }

    public PuestoDeTrabajo removeRequerimiento(Requisito requisito) {
        this.requerimientos.remove(requisito);
        requisito.getPuestoDeTrabajos().remove(this);
        return this;
    }

    public void setRequerimientos(Set<Requisito> requisitos) {
        this.requerimientos = requisitos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PuestoDeTrabajo)) {
            return false;
        }
        return id != null && id.equals(((PuestoDeTrabajo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PuestoDeTrabajo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
