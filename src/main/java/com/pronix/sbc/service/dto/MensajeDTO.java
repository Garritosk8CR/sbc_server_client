package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.pronix.sbc.domain.enumeration.EstadoMensaje;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Mensaje} entity.
 */
@ApiModel(description = "Mensaje entity.\n@author Max Rojas.")
public class MensajeDTO implements Serializable {

    private Long id;

    @NotNull
    private String mensaje;

    @NotNull
    private String fechaEmision;

    private EstadoMensaje estadoMensaje;


    private Long conversacionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public EstadoMensaje getEstadoMensaje() {
        return estadoMensaje;
    }

    public void setEstadoMensaje(EstadoMensaje estadoMensaje) {
        this.estadoMensaje = estadoMensaje;
    }

    public Long getConversacionId() {
        return conversacionId;
    }

    public void setConversacionId(Long conversacionId) {
        this.conversacionId = conversacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MensajeDTO mensajeDTO = (MensajeDTO) o;
        if (mensajeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mensajeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MensajeDTO{" +
            "id=" + getId() +
            ", mensaje='" + getMensaje() + "'" +
            ", fechaEmision='" + getFechaEmision() + "'" +
            ", estadoMensaje='" + getEstadoMensaje() + "'" +
            ", conversacion=" + getConversacionId() +
            "}";
    }
}
