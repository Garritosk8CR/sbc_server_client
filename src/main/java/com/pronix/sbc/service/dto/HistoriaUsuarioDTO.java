package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pronix.sbc.domain.HistoriaUsuario} entity.
 */
@ApiModel(description = "HistoriaUsuario entity.\n@author Max Rojas.")
public class HistoriaUsuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private LocalDate fechaCreacion;

    private LocalDate fechaConclucion;

    private String sprint;

    private Boolean isEpic;


    private Long perfilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaConclucion() {
        return fechaConclucion;
    }

    public void setFechaConclucion(LocalDate fechaConclucion) {
        this.fechaConclucion = fechaConclucion;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public Boolean isIsEpic() {
        return isEpic;
    }

    public void setIsEpic(Boolean isEpic) {
        this.isEpic = isEpic;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HistoriaUsuarioDTO historiaUsuarioDTO = (HistoriaUsuarioDTO) o;
        if (historiaUsuarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiaUsuarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoriaUsuarioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaConclucion='" + getFechaConclucion() + "'" +
            ", sprint='" + getSprint() + "'" +
            ", isEpic='" + isIsEpic() + "'" +
            ", perfil=" + getPerfilId() +
            "}";
    }
}
