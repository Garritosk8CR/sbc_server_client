package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.pronix.sbc.domain.enumeration.Categoria;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Curso} entity.
 */
@ApiModel(description = "Curso entity.\n@author Max Rojas.")
public class CursoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String slug;

    @NotNull
    private String descripcion;

    @NotNull
    private Categoria categoria;

    @NotNull
    private String duracion;

    @NotNull
    private String totalDeArticulos;

    @NotNull
    private LocalDate fechaActualizacion;


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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTotalDeArticulos() {
        return totalDeArticulos;
    }

    public void setTotalDeArticulos(String totalDeArticulos) {
        this.totalDeArticulos = totalDeArticulos;
    }

    public LocalDate getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDate fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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

        CursoDTO cursoDTO = (CursoDTO) o;
        if (cursoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cursoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CursoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", slug='" + getSlug() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", totalDeArticulos='" + getTotalDeArticulos() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            ", perfil=" + getPerfilId() +
            "}";
    }
}
