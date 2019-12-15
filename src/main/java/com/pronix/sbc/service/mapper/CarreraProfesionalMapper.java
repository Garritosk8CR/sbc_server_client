package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.CarreraProfesionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarreraProfesional} and its DTO {@link CarreraProfesionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarreraProfesionalMapper extends EntityMapper<CarreraProfesionalDTO, CarreraProfesional> {


    @Mapping(target = "puestos", ignore = true)
    @Mapping(target = "removePuesto", ignore = true)
    CarreraProfesional toEntity(CarreraProfesionalDTO carreraProfesionalDTO);

    default CarreraProfesional fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarreraProfesional carreraProfesional = new CarreraProfesional();
        carreraProfesional.setId(id);
        return carreraProfesional;
    }
}
