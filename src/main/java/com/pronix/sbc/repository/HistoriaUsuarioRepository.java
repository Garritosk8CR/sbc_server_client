package com.pronix.sbc.repository;
import com.pronix.sbc.domain.HistoriaUsuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HistoriaUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriaUsuarioRepository extends JpaRepository<HistoriaUsuario, Long> {

}
