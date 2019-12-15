package com.pronix.sbc.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pronix.sbc.domain.Perfil} entity.
 */
@ApiModel(description = "Perfil entity.\n@author Max Rojas.")
public class PerfilDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String primerApellido;

    @NotNull
    private String segundoApellido;

    @NotNull
    private String edad;

    @NotNull
    private String estadoCivil;

    @NotNull
    private String sexo;

    @NotNull
    private String telefonoCelular;

    @NotNull
    private String telefonoFijo;

    @NotNull
    private String correoElectronico;

    @NotNull
    private String direccion;

    @NotNull
    private String cedula;

    @NotNull
    private LocalDate fechaIngreso;

    private LocalDate fechaSalida;

    @NotNull
    private String foto;


    private Long carreraProfesionalId;

    private String carreraProfesionalNombre;

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

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getCarreraProfesionalId() {
        return carreraProfesionalId;
    }

    public void setCarreraProfesionalId(Long carreraProfesionalId) {
        this.carreraProfesionalId = carreraProfesionalId;
    }

    public String getCarreraProfesionalNombre() {
        return carreraProfesionalNombre;
    }

    public void setCarreraProfesionalNombre(String carreraProfesionalNombre) {
        this.carreraProfesionalNombre = carreraProfesionalNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PerfilDTO perfilDTO = (PerfilDTO) o;
        if (perfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", edad='" + getEdad() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", telefonoCelular='" + getTelefonoCelular() + "'" +
            ", telefonoFijo='" + getTelefonoFijo() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", cedula='" + getCedula() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", foto='" + getFoto() + "'" +
            ", carreraProfesional=" + getCarreraProfesionalId() +
            ", carreraProfesional='" + getCarreraProfesionalNombre() + "'" +
            "}";
    }
}
