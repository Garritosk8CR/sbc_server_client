package com.pronix.sbc.service.mapper;

import com.pronix.sbc.domain.*;
import com.pronix.sbc.service.dto.PublicacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Publicacion} and its DTO {@link PublicacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface PublicacionMapper extends EntityMapper<PublicacionDTO, Publicacion> {

    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "perfil.nombre", target = "perfilNombre")
    PublicacionDTO toDto(Publicacion publicacion);

    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "removeComentario", ignore = true)
    @Mapping(source = "perfilId", target = "perfil")
    Publicacion toEntity(PublicacionDTO publicacionDTO);

    default Publicacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }
}
