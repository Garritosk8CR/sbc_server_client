package com.pronix.sbc.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * HistoriaUsuario entity.\n@author Max Rojas.
 */
@Entity
@Table(name = "historia_usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HistoriaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_conclucion")
    private LocalDate fechaConclucion;

    @Column(name = "sprint")
    private String sprint;

    @Column(name = "is_epic")
    private Boolean isEpic;

    @ManyToOne
    @JsonIgnoreProperties("historiaUsuarios")
    private Perfil perfil;

    @OneToMany(mappedBy = "historiaUsuario")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarea> tareas = new HashSet<>();

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

    public HistoriaUsuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public HistoriaUsuario descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public HistoriaUsuario fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaConclucion() {
        return fechaConclucion;
    }

    public HistoriaUsuario fechaConclucion(LocalDate fechaConclucion) {
        this.fechaConclucion = fechaConclucion;
        return this;
    }

    public void setFechaConclucion(LocalDate fechaConclucion) {
        this.fechaConclucion = fechaConclucion;
    }

    public String getSprint() {
        return sprint;
    }

    public HistoriaUsuario sprint(String sprint) {
        this.sprint = sprint;
        return this;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public Boolean isIsEpic() {
        return isEpic;
    }

    public HistoriaUsuario isEpic(Boolean isEpic) {
        this.isEpic = isEpic;
        return this;
    }

    public void setIsEpic(Boolean isEpic) {
        this.isEpic = isEpic;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public HistoriaUsuario perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public HistoriaUsuario tareas(Set<Tarea> tareas) {
        this.tareas = tareas;
        return this;
    }

    public HistoriaUsuario addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setHistoriaUsuario(this);
        return this;
    }

    public HistoriaUsuario removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.setHistoriaUsuario(null);
        return this;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoriaUsuario)) {
            return false;
        }
        return id != null && id.equals(((HistoriaUsuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HistoriaUsuario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaConclucion='" + getFechaConclucion() + "'" +
            ", sprint='" + getSprint() + "'" +
            ", isEpic='" + isIsEpic() + "'" +
            "}";
    }
}
