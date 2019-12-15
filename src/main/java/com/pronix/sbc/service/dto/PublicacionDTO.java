package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.pronix.sbc.domain.enumeration.TipoPublicacion;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Publicacion} entity.
 */
@ApiModel(description = "Publicacion entity.\n@author Max Rojas.")
public class PublicacionDTO implements Serializable {

    private Long id;

    private LocalDate fechaPublicacion;

    @NotNull
    private String contenido;

    private TipoPublicacion tipoPublicacion;


    private Long perfilId;

    private String perfilNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoPublicacion getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public String getPerfilNombre() {
        return perfilNombre;
    }

    public void setPerfilNombre(String perfilNombre) {
        this.perfilNombre = perfilNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublicacionDTO publicacionDTO = (PublicacionDTO) o;
        if (publicacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicacionDTO{" +
            "id=" + getId() +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", tipoPublicacion='" + getTipoPublicacion() + "'" +
            ", perfil=" + getPerfilId() +
            ", perfil='" + getPerfilNombre() + "'" +
            "}";
    }
}
