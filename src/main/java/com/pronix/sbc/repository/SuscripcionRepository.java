package com.pronix.sbc.repository;
import com.pronix.sbc.domain.Suscripcion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Suscripcion entity.
 */
@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

    @Query(value = "select distinct suscripcion from Suscripcion suscripcion left join fetch suscripcion.suscriptors",
        countQuery = "select count(distinct suscripcion) from Suscripcion suscripcion")
    Page<Suscripcion> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct suscripcion from Suscripcion suscripcion left join fetch suscripcion.suscriptors")
    List<Suscripcion> findAllWithEagerRelationships();

    @Query("select suscripcion from Suscripcion suscripcion left join fetch suscripcion.suscriptors where suscripcion.id =:id")
    Optional<Suscripcion> findOneWithEagerRelationships(@Param("id") Long id);

}
