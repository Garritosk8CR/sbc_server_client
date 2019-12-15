package com.pronix.sbc.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.pronix.sbc.domain.enumeration.EstadoSuscripcion;

/**
 * Suscripcion entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "suscripcion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_suscripcion")
    private LocalDate fechaSuscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_suscripcion")
    private EstadoSuscripcion estadoSuscripcion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "suscripcion_suscriptor",
               joinColumns = @JoinColumn(name = "suscripcion_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "suscriptor_id", referencedColumnName = "id"))
    private Set<Perfil> suscriptors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public Suscripcion fechaSuscripcion(LocalDate fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
        return this;
    }

    public void setFechaSuscripcion(LocalDate fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public EstadoSuscripcion getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public Suscripcion estadoSuscripcion(EstadoSuscripcion estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
        return this;
    }

    public void setEstadoSuscripcion(EstadoSuscripcion estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
    }

    public Set<Perfil> getSuscriptors() {
        return suscriptors;
    }

    public Suscripcion suscriptors(Set<Perfil> perfils) {
        this.suscriptors = perfils;
        return this;
    }

    public Suscripcion addSuscriptor(Perfil perfil) {
        this.suscriptors.add(perfil);
        perfil.getSuscripcions().add(this);
        return this;
    }

    public Suscripcion removeSuscriptor(Perfil perfil) {
        this.suscriptors.remove(perfil);
        perfil.getSuscripcions().remove(this);
        return this;
    }

    public void setSuscriptors(Set<Perfil> perfils) {
        this.suscriptors = perfils;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suscripcion)) {
            return false;
        }
        return id != null && id.equals(((Suscripcion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Suscripcion{" +
            "id=" + getId() +
            ", fechaSuscripcion='" + getFechaSuscripcion() + "'" +
            ", estadoSuscripcion='" + getEstadoSuscripcion() + "'" +
            "}";
    }
}
