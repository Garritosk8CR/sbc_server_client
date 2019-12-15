package com.pronix.sbc.repository;
import com.pronix.sbc.domain.CarreraProfesional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarreraProfesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarreraProfesionalRepository extends JpaRepository<CarreraProfesional, Long> {

}
