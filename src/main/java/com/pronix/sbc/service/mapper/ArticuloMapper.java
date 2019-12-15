package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.ArticuloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Articulo} and its DTO {@link ArticuloDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class, CursoMapper.class})
public interface ArticuloMapper extends EntityMapper<ArticuloDTO, Articulo> {

    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "curso.id", target = "cursoId")
    ArticuloDTO toDto(Articulo articulo);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "cursoId", target = "curso")
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "removeComentario", ignore = true)
    Articulo toEntity(ArticuloDTO articuloDTO);

    default Articulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Articulo articulo = new Articulo();
        articulo.setId(id);
        return articulo;
    }
}
