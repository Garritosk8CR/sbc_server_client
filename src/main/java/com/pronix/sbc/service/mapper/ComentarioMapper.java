package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.ComentarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comentario} and its DTO {@link ComentarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class, TareaMapper.class, ArticuloMapper.class, PublicacionMapper.class})
public interface ComentarioMapper extends EntityMapper<ComentarioDTO, Comentario> {

    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "tarea.id", target = "tareaId")
    @Mapping(source = "articulo.id", target = "articuloId")
    @Mapping(source = "publicacion.id", target = "publicacionId")
    ComentarioDTO toDto(Comentario comentario);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "tareaId", target = "tarea")
    @Mapping(source = "articuloId", target = "articulo")
    @Mapping(source = "publicacionId", target = "publicacion")
    Comentario toEntity(ComentarioDTO comentarioDTO);

    default Comentario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comentario comentario = new Comentario();
        comentario.setId(id);
        return comentario;
    }
}
