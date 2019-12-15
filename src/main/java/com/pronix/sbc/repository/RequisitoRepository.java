package com.pronix.sbc.repository;
import com.pronix.sbc.domain.Requisito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Requisito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {

}
