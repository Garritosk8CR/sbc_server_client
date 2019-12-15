package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Perfil entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "perfil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @NotNull
    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    @NotNull
    @Column(name = "edad", nullable = false)
    private String edad;

    @NotNull
    @Column(name = "estado_civil", nullable = false)
    private String estadoCivil;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "telefono_celular", nullable = false)
    private String telefonoCelular;

    @NotNull
    @Column(name = "telefono_fijo", nullable = false)
    private String telefonoFijo;

    @NotNull
    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "cedula", nullable = false)
    private String cedula;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @NotNull
    @Column(name = "foto", nullable = false)
    private String foto;

    @OneToOne
    @JoinColumn(unique = true)
    private CarreraProfesional carreraProfesional;

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HistoriaUsuario> historiaUsuarios = new HashSet<>();

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Conversacion> conversacions = new HashSet<>();

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notificacion> notificacions = new HashSet<>();

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Curso> cursos = new HashSet<>();

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Articulo> articulos = new HashSet<>();

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comentario> comentarios = new HashSet<>();

    @ManyToMany(mappedBy = "suscriptors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Suscripcion> suscripcions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Perfil nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public Perfil primerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Perfil segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEdad() {
        return edad;
    }

    public Perfil edad(String edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public Perfil estadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public Perfil sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public Perfil telefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
        return this;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public Perfil telefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
        return this;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Perfil correoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public Perfil direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public Perfil cedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Perfil fechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public Perfil fechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFoto() {
        return foto;
    }

    public Perfil foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public CarreraProfesional getCarreraProfesional() {
        return carreraProfesional;
    }

    public Perfil carreraProfesional(CarreraProfesional carreraProfesional) {
        this.carreraProfesional = carreraProfesional;
        return this;
    }

    public void setCarreraProfesional(CarreraProfesional carreraProfesional) {
        this.carreraProfesional = carreraProfesional;
    }

    public Set<HistoriaUsuario> getHistoriaUsuarios() {
        return historiaUsuarios;
    }

    public Perfil historiaUsuarios(Set<HistoriaUsuario> historiaUsuarios) {
        this.historiaUsuarios = historiaUsuarios;
        return this;
    }

    public Perfil addHistoriaUsuario(HistoriaUsuario historiaUsuario) {
        this.historiaUsuarios.add(historiaUsuario);
        historiaUsuario.setPerfil(this);
        return this;
    }

    public Perfil removeHistoriaUsuario(HistoriaUsuario historiaUsuario) {
        this.historiaUsuarios.remove(historiaUsuario);
        historiaUsuario.setPerfil(null);
        return this;
    }

    public void setHistoriaUsuarios(Set<HistoriaUsuario> historiaUsuarios) {
        this.historiaUsuarios = historiaUsuarios;
    }

    public Set<Conversacion> getConversacions() {
        return conversacions;
    }

    public Perfil conversacions(Set<Conversacion> conversacions) {
        this.conversacions = conversacions;
        return this;
    }

    public Perfil addConversacion(Conversacion conversacion) {
        this.conversacions.add(conversacion);
        conversacion.setPerfil(this);
        return this;
    }

    public Perfil removeConversacion(Conversacion conversacion) {
        this.conversacions.remove(conversacion);
        conversacion.setPerfil(null);
        return this;
    }

    public void setConversacions(Set<Conversacion> conversacions) {
        this.conversacions = conversacions;
    }

    public Set<Notificacion> getNotificacions() {
        return notificacions;
    }

    public Perfil notificacions(Set<Notificacion> notificacions) {
        this.notificacions = notificacions;
        return this;
    }

    public Perfil addNotificacion(Notificacion notificacion) {
        this.notificacions.add(notificacion);
        notificacion.setPerfil(this);
        return this;
    }

    public Perfil removeNotificacion(Notificacion notificacion) {
        this.notificacions.remove(notificacion);
        notificacion.setPerfil(null);
        return this;
    }

    public void setNotificacions(Set<Notificacion> notificacions) {
        this.notificacions = notificacions;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public Perfil cursos(Set<Curso> cursos) {
        this.cursos = cursos;
        return this;
    }

    public Perfil addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setPerfil(this);
        return this;
    }

    public Perfil removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setPerfil(null);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public Set<Articulo> getArticulos() {
        return articulos;
    }

    public Perfil articulos(Set<Articulo> articulos) {
        this.articulos = articulos;
        return this;
    }

    public Perfil addArticulo(Articulo articulo) {
        this.articulos.add(articulo);
        articulo.setPerfil(this);
        return this;
    }

    public Perfil removeArticulo(Articulo articulo) {
        this.articulos.remove(articulo);
        articulo.setPerfil(null);
        return this;
    }

    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Perfil comentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    public Perfil addComentario(Comentario comentario) {
        this.comentarios.add(comentario);
        comentario.setPerfil(this);
        return this;
    }

    public Perfil removeComentario(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setPerfil(null);
        return this;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Set<Suscripcion> getSuscripcions() {
        return suscripcions;
    }

    public Perfil suscripcions(Set<Suscripcion> suscripcions) {
        this.suscripcions = suscripcions;
        return this;
    }

    public Perfil addSuscripcion(Suscripcion suscripcion) {
        this.suscripcions.add(suscripcion);
        suscripcion.getSuscriptors().add(this);
        return this;
    }

    public Perfil removeSuscripcion(Suscripcion suscripcion) {
        this.suscripcions.remove(suscripcion);
        suscripcion.getSuscriptors().remove(this);
        return this;
    }

    public void setSuscripcions(Set<Suscripcion> suscripcions) {
        this.suscripcions = suscripcions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Perfil)) {
            return false;
        }
        return id != null && id.equals(((Perfil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Perfil{" +
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
            "}";
    }
}
