package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.pronix.sbc.domain.enumeration.EstadoMensaje;

/**
 * Mensaje entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "mensaje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private String fechaEmision;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mensaje")
    private EstadoMensaje estadoMensaje;

    @ManyToOne
    @JsonIgnoreProperties("mensajes")
    private Conversacion conversacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Mensaje mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public Mensaje fechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
        return this;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public EstadoMensaje getEstadoMensaje() {
        return estadoMensaje;
    }

    public Mensaje estadoMensaje(EstadoMensaje estadoMensaje) {
        this.estadoMensaje = estadoMensaje;
        return this;
    }

    public void setEstadoMensaje(EstadoMensaje estadoMensaje) {
        this.estadoMensaje = estadoMensaje;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }

    public Mensaje conversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
        return this;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mensaje)) {
            return false;
        }
        return id != null && id.equals(((Mensaje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
            "id=" + getId() +
            ", mensaje='" + getMensaje() + "'" +
            ", fechaEmision='" + getFechaEmision() + "'" +
            ", estadoMensaje='" + getEstadoMensaje() + "'" +
            "}";
    }
}
