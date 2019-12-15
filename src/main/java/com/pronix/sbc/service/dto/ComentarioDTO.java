package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Comentario} entity.
 */
@ApiModel(description = "Comentario entity.\n@author Max Rojas.")
public class ComentarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String autor;

    @NotNull
    private String avatar;

    @NotNull
    private LocalDate fechaCreacion;

    @NotNull
    private String contenido;


    private Long perfilId;

    private Long tareaId;

    private Long articuloId;

    private Long publicacionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }

    public Long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Long articuloId) {
        this.articuloId = articuloId;
    }

    public Long getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(Long publicacionId) {
        this.publicacionId = publicacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComentarioDTO comentarioDTO = (ComentarioDTO) o;
        if (comentarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
            "id=" + getId() +
            ", autor='" + getAutor() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", perfil=" + getPerfilId() +
            ", tarea=" + getTareaId() +
            ", articulo=" + getArticuloId() +
            ", publicacion=" + getPublicacionId() +
            "}";
    }
}
