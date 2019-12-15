package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.ConversacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conversacion} and its DTO {@link ConversacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface ConversacionMapper extends EntityMapper<ConversacionDTO, Conversacion> {

    @Mapping(source = "perfil.id", target = "perfilId")
    ConversacionDTO toDto(Conversacion conversacion);

    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(target = "mensajes", ignore = true)
    @Mapping(target = "removeMensaje", ignore = true)
    Conversacion toEntity(ConversacionDTO conversacionDTO);

    default Conversacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conversacion conversacion = new Conversacion();
        conversacion.setId(id);
        return conversacion;
    }
}
