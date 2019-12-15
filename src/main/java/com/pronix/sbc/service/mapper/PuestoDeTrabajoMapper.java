package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.PuestoDeTrabajoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PuestoDeTrabajo} and its DTO {@link PuestoDeTrabajoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CarreraProfesionalMapper.class, RequisitoMapper.class})
public interface PuestoDeTrabajoMapper extends EntityMapper<PuestoDeTrabajoDTO, PuestoDeTrabajo> {

    @Mapping(source = "carreraProfesional.id", target = "carreraProfesionalId")
    PuestoDeTrabajoDTO toDto(PuestoDeTrabajo puestoDeTrabajo);

    @Mapping(source = "carreraProfesionalId", target = "carreraProfesional")
    @Mapping(target = "removeRequerimiento", ignore = true)
    PuestoDeTrabajo toEntity(PuestoDeTrabajoDTO puestoDeTrabajoDTO);

    default PuestoDeTrabajo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PuestoDeTrabajo puestoDeTrabajo = new PuestoDeTrabajo();
        puestoDeTrabajo.setId(id);
        return puestoDeTrabajo;
    }
}
