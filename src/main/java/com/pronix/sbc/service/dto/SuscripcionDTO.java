package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pronix.sbc.domain.enumeration.EstadoSuscripcion;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Suscripcion} entity.
 */
@ApiModel(description = "Suscripcion entity.\n@author Max Rojas.")
public class SuscripcionDTO implements Serializable {

    private Long id;

    private LocalDate fechaSuscripcion;

    private EstadoSuscripcion estadoSuscripcion;


    private Set<PerfilDTO> suscriptors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(LocalDate fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public EstadoSuscripcion getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(EstadoSuscripcion estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
    }

    public Set<PerfilDTO> getSuscriptors() {
        return suscriptors;
    }

    public void setSuscriptors(Set<PerfilDTO> perfils) {
        this.suscriptors = perfils;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuscripcionDTO suscripcionDTO = (SuscripcionDTO) o;
        if (suscripcionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suscripcionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SuscripcionDTO{" +
            "id=" + getId() +
            ", fechaSuscripcion='" + getFechaSuscripcion() + "'" +
            ", estadoSuscripcion='" + getEstadoSuscripcion() + "'" +
            "}";
    }
}
