package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer>{
    List<SessionEntity> findByAgendaIdAndEndTimeAfter(Integer agendaId, LocalDateTime now);
}
