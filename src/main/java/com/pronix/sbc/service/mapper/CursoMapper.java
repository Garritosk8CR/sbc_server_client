package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.CursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {

    @Mapping(source = "perfil.id", target = "perfilId")
    CursoDTO toDto(Curso curso);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(target = "articulos", ignore = true)
    @Mapping(target = "removeArticulo", ignore = true)
    Curso toEntity(CursoDTO cursoDTO);

    default Curso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
