package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaEntity, Integer>{
}
