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

import com.pronix.sbc.domain.enumeration.TipoPublicacion;

/**
 * Publicacion entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "publicacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @NotNull
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_publicacion")
    private TipoPublicacion tipoPublicacion;

    @OneToMany(mappedBy = "publicacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comentario> comentarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("publicacions")
    private Perfil perfil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Publicacion fechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
        return this;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getContenido() {
        return contenido;
    }

    public Publicacion contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoPublicacion getTipoPublicacion() {
        return tipoPublicacion;
    }

    public Publicacion tipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
        return this;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Publicacion comentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    public Publicacion addComentario(Comentario comentario) {
        this.comentarios.add(comentario);
        comentario.setPublicacion(this);
        return this;
    }

    public Publicacion removeComentario(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setPublicacion(null);
        return this;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Publicacion perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publicacion)) {
            return false;
        }
        return id != null && id.equals(((Publicacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
            "id=" + getId() +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", tipoPublicacion='" + getTipoPublicacion() + "'" +
            "}";
    }
}
