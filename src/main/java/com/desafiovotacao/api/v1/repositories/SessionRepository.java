package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer>{
}
