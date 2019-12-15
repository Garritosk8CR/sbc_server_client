package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.pronix.sbc.domain.enumeration.EstadoTarea;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Tarea} entity.
 */
@ApiModel(description = "Tarea entity.\n@author Max Rojas.")
public class TareaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private LocalDate fechaCreacion;

    private LocalDate fechaConclucion;

    private EstadoTarea estadoTarea;


    private Long historiaUsuarioId;

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

    public EstadoTarea getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(EstadoTarea estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public Long getHistoriaUsuarioId() {
        return historiaUsuarioId;
    }

    public void setHistoriaUsuarioId(Long historiaUsuarioId) {
        this.historiaUsuarioId = historiaUsuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TareaDTO tareaDTO = (TareaDTO) o;
        if (tareaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tareaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaConclucion='" + getFechaConclucion() + "'" +
            ", estadoTarea='" + getEstadoTarea() + "'" +
            ", historiaUsuario=" + getHistoriaUsuarioId() +
            "}";
    }
}
