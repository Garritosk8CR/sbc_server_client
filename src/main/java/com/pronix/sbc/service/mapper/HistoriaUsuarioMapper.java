package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.HistoriaUsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistoriaUsuario} and its DTO {@link HistoriaUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface HistoriaUsuarioMapper extends EntityMapper<HistoriaUsuarioDTO, HistoriaUsuario> {

    @Mapping(source = "perfil.id", target = "perfilId")
    HistoriaUsuarioDTO toDto(HistoriaUsuario historiaUsuario);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(target = "tareas", ignore = true)
    @Mapping(target = "removeTarea", ignore = true)
    HistoriaUsuario toEntity(HistoriaUsuarioDTO historiaUsuarioDTO);

    default HistoriaUsuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistoriaUsuario historiaUsuario = new HistoriaUsuario();
        historiaUsuario.setId(id);
        return historiaUsuario;
    }
}
