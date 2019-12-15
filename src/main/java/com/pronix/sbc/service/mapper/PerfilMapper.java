package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.PerfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Perfil} and its DTO {@link PerfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {CarreraProfesionalMapper.class})
public interface PerfilMapper extends EntityMapper<PerfilDTO, Perfil> {

    @Mapping(source = "carreraProfesional.id", target = "carreraProfesionalId")
    @Mapping(source = "carreraProfesional.nombre", target = "carreraProfesionalNombre")
    PerfilDTO toDto(Perfil perfil);

    @Mapping(source = "carreraProfesionalId", target = "carreraProfesional")
    @Mapping(target = "historiaUsuarios", ignore = true)
    @Mapping(target = "removeHistoriaUsuario", ignore = true)
    @Mapping(target = "conversacions", ignore = true)
    @Mapping(target = "removeConversacion", ignore = true)
    @Mapping(target = "notificacions", ignore = true)
    @Mapping(target = "removeNotificacion", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "removeCurso", ignore = true)
    @Mapping(target = "articulos", ignore = true)
    @Mapping(target = "removeArticulo", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "removeComentario", ignore = true)
    @Mapping(target = "suscripcions", ignore = true)
    @Mapping(target = "removeSuscripcion", ignore = true)
    Perfil toEntity(PerfilDTO perfilDTO);

    default Perfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }
}
