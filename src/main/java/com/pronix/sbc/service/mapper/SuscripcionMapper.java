package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.SuscripcionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suscripcion} and its DTO {@link SuscripcionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface SuscripcionMapper extends EntityMapper<SuscripcionDTO, Suscripcion> {


    @Mapping(target = "removeSuscriptor", ignore = true)

    default Suscripcion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setId(id);
        return suscripcion;
    }
}
