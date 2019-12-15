package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Comentario entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "comentario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "autor", nullable = false)
    private String autor;

    @NotNull
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @NotNull
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    private Perfil perfil;

    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    private Tarea tarea;

    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    private Articulo articulo;

    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    private Publicacion publicacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public Comentario autor(String autor) {
        this.autor = autor;
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAvatar() {
        return avatar;
    }

    public Comentario avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Comentario fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getContenido() {
        return contenido;
    }

    public Comentario contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Comentario perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public Comentario tarea(Tarea tarea) {
        this.tarea = tarea;
        return this;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public Comentario articulo(Articulo articulo) {
        this.articulo = articulo;
        return this;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public Comentario publicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
        return this;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comentario)) {
            return false;
        }
        return id != null && id.equals(((Comentario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comentario{" +
            "id=" + getId() +
            ", autor='" + getAutor() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", contenido='" + getContenido() + "'" +
            "}";
    }
}
