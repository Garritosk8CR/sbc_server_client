package com.pronix.sbc.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * CarreraProfesional entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "carrera_profesional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CarreraProfesional implements Serializable {

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

    @OneToMany(mappedBy = "carreraProfesional")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PuestoDeTrabajo> puestos = new HashSet<>();

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

    public CarreraProfesional nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CarreraProfesional descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<PuestoDeTrabajo> getPuestos() {
        return puestos;
    }

    public CarreraProfesional puestos(Set<PuestoDeTrabajo> puestoDeTrabajos) {
        this.puestos = puestoDeTrabajos;
        return this;
    }

    public CarreraProfesional addPuesto(PuestoDeTrabajo puestoDeTrabajo) {
        this.puestos.add(puestoDeTrabajo);
        puestoDeTrabajo.setCarreraProfesional(this);
        return this;
    }

    public CarreraProfesional removePuesto(PuestoDeTrabajo puestoDeTrabajo) {
        this.puestos.remove(puestoDeTrabajo);
        puestoDeTrabajo.setCarreraProfesional(null);
        return this;
    }

    public void setPuestos(Set<PuestoDeTrabajo> puestoDeTrabajos) {
        this.puestos = puestoDeTrabajos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarreraProfesional)) {
            return false;
        }
        return id != null && id.equals(((CarreraProfesional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CarreraProfesional{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
