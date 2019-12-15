package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Conversacion} entity.
 */
@ApiModel(description = "Conversacion entity.\n@author Max Rojas.")
public class ConversacionDTO implements Serializable {

    private Long id;

    @NotNull
    private String usuario1;

    @NotNull
    private String usuario2;

    @NotNull
    private LocalDate fechaDeConversacion;


    private Long perfilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(String usuario1) {
        this.usuario1 = usuario1;
    }

    public String getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(String usuario2) {
        this.usuario2 = usuario2;
    }

    public LocalDate getFechaDeConversacion() {
        return fechaDeConversacion;
    }

    public void setFechaDeConversacion(LocalDate fechaDeConversacion) {
        this.fechaDeConversacion = fechaDeConversacion;
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

        ConversacionDTO conversacionDTO = (ConversacionDTO) o;
        if (conversacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConversacionDTO{" +
            "id=" + getId() +
            ", usuario1='" + getUsuario1() + "'" +
            ", usuario2='" + getUsuario2() + "'" +
            ", fechaDeConversacion='" + getFechaDeConversacion() + "'" +
            ", perfil=" + getPerfilId() +
            "}";
    }
}
