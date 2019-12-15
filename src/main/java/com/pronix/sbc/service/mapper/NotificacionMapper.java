package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.NotificacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notificacion} and its DTO {@link NotificacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface NotificacionMapper extends EntityMapper<NotificacionDTO, Notificacion> {

    @Mapping(source = "perfil.id", target = "perfilId")
    NotificacionDTO toDto(Notificacion notificacion);

    @Mapping(source = "perfilId", target = "perfil")
    Notificacion toEntity(NotificacionDTO notificacionDTO);

    default Notificacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setId(id);
        return notificacion;
    }
}
