package com.pronix.sbc.repository;
import com.pronix.sbc.domain.PuestoDeTrabajo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PuestoDeTrabajo entity.
 */
@Repository
public interface PuestoDeTrabajoRepository extends JpaRepository<PuestoDeTrabajo, Long> {

    @Query(value = "select distinct puestoDeTrabajo from PuestoDeTrabajo puestoDeTrabajo left join fetch puestoDeTrabajo.requerimientos",
        countQuery = "select count(distinct puestoDeTrabajo) from PuestoDeTrabajo puestoDeTrabajo")
    Page<PuestoDeTrabajo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct puestoDeTrabajo from PuestoDeTrabajo puestoDeTrabajo left join fetch puestoDeTrabajo.requerimientos")
    List<PuestoDeTrabajo> findAllWithEagerRelationships();

    @Query("select puestoDeTrabajo from PuestoDeTrabajo puestoDeTrabajo left join fetch puestoDeTrabajo.requerimientos where puestoDeTrabajo.id =:id")
    Optional<PuestoDeTrabajo> findOneWithEagerRelationships(@Param("id") Long id);

}
