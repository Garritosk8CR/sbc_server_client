package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.MensajeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mensaje} and its DTO {@link MensajeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConversacionMapper.class})
public interface MensajeMapper extends EntityMapper<MensajeDTO, Mensaje> {

    @Mapping(source = "conversacion.id", target = "conversacionId")
    MensajeDTO toDto(Mensaje mensaje);

    @Mapping(source = "conversacionId", target = "conversacion")
    Mensaje toEntity(MensajeDTO mensajeDTO);

    default Mensaje fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mensaje mensaje = new Mensaje();
        mensaje.setId(id);
        return mensaje;
    }
}
