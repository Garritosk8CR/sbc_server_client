package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.pronix.sbc.domain.enumeration.EstadoMensaje;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Notificacion} entity.
 */
@ApiModel(description = "Notificacion entity.\n@author Max Rojas.")
public class NotificacionDTO implements Serializable {

    private Long id;

    private String origen;

    private String destino;

    private String tipo;

    private LocalDate fechaEmision;

    private EstadoMensaje estadoMensaje;


    private Long perfilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public EstadoMensaje getEstadoMensaje() {
        return estadoMensaje;
    }

    public void setEstadoMensaje(EstadoMensaje estadoMensaje) {
        this.estadoMensaje = estadoMensaje;
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

        NotificacionDTO notificacionDTO = (NotificacionDTO) o;
        if (notificacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificacionDTO{" +
            "id=" + getId() +
            ", origen='" + getOrigen() + "'" +
            ", destino='" + getDestino() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaEmision='" + getFechaEmision() + "'" +
            ", estadoMensaje='" + getEstadoMensaje() + "'" +
            ", perfil=" + getPerfilId() +
            "}";
    }
}
