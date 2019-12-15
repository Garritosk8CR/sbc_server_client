package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.RequisitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requisito} and its DTO {@link RequisitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RequisitoMapper extends EntityMapper<RequisitoDTO, Requisito> {


    @Mapping(target = "puestoDeTrabajos", ignore = true)
    @Mapping(target = "removePuestoDeTrabajo", ignore = true)
    Requisito toEntity(RequisitoDTO requisitoDTO);

    default Requisito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Requisito requisito = new Requisito();
        requisito.setId(id);
        return requisito;
    }
}
