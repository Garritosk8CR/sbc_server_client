package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Conversacion entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "conversacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "usuario_1", nullable = false)
    private String usuario1;

    @NotNull
    @Column(name = "usuario_2", nullable = false)
    private String usuario2;

    @NotNull
    @Column(name = "fecha_de_conversacion", nullable = false)
    private LocalDate fechaDeConversacion;

    @ManyToOne
    @JsonIgnoreProperties("conversacions")
    private Perfil perfil;

    @OneToMany(mappedBy = "conversacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mensaje> mensajes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario1() {
        return usuario1;
    }

    public Conversacion usuario1(String usuario1) {
        this.usuario1 = usuario1;
        return this;
    }

    public void setUsuario1(String usuario1) {
        this.usuario1 = usuario1;
    }

    public String getUsuario2() {
        return usuario2;
    }

    public Conversacion usuario2(String usuario2) {
        this.usuario2 = usuario2;
        return this;
    }

    public void setUsuario2(String usuario2) {
        this.usuario2 = usuario2;
    }

    public LocalDate getFechaDeConversacion() {
        return fechaDeConversacion;
    }

    public Conversacion fechaDeConversacion(LocalDate fechaDeConversacion) {
        this.fechaDeConversacion = fechaDeConversacion;
        return this;
    }

    public void setFechaDeConversacion(LocalDate fechaDeConversacion) {
        this.fechaDeConversacion = fechaDeConversacion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Conversacion perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public Conversacion mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public Conversacion addMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
        mensaje.setConversacion(this);
        return this;
    }

    public Conversacion removeMensaje(Mensaje mensaje) {
        this.mensajes.remove(mensaje);
        mensaje.setConversacion(null);
        return this;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversacion)) {
            return false;
        }
        return id != null && id.equals(((Conversacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conversacion{" +
            "id=" + getId() +
            ", usuario1='" + getUsuario1() + "'" +
            ", usuario2='" + getUsuario2() + "'" +
            ", fechaDeConversacion='" + getFechaDeConversacion() + "'" +
            "}";
    }
}
