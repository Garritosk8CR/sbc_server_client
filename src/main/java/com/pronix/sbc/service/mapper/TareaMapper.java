package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.TareaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarea} and its DTO {@link TareaDTO}.
 */
@Mapper(componentModel = "spring", uses = {HistoriaUsuarioMapper.class})
public interface TareaMapper extends EntityMapper<TareaDTO, Tarea> {

    @Mapping(source = "historiaUsuario.id", target = "historiaUsuarioId")
    TareaDTO toDto(Tarea tarea);

    @Mapping(source = "historiaUsuarioId", target = "historiaUsuario")
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "removeComentario", ignore = true)
    Tarea toEntity(TareaDTO tareaDTO);

    default Tarea fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarea tarea = new Tarea();
        tarea.setId(id);
        return tarea;
    }
}
