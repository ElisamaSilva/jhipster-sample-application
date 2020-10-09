package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Entregador;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Entregador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}
