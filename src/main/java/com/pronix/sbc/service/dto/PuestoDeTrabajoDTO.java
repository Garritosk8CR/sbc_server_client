package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.pronix.sbc.domain.PuestoDeTrabajo} entity.
 */
@ApiModel(description = "PuestoDeTrabajo entity.\n@author Max Rojas.")
public class PuestoDeTrabajoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;


    private Long carreraProfesionalId;

    private Set<RequisitoDTO> requerimientos = new HashSet<>();

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

    public Long getCarreraProfesionalId() {
        return carreraProfesionalId;
    }

    public void setCarreraProfesionalId(Long carreraProfesionalId) {
        this.carreraProfesionalId = carreraProfesionalId;
    }

    public Set<RequisitoDTO> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(Set<RequisitoDTO> requisitos) {
        this.requerimientos = requisitos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PuestoDeTrabajoDTO puestoDeTrabajoDTO = (PuestoDeTrabajoDTO) o;
        if (puestoDeTrabajoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puestoDeTrabajoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuestoDeTrabajoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", carreraProfesional=" + getCarreraProfesionalId() +
            "}";
    }
}
