package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {
    Optional<VoteEntity> findByAssociateIdAndAgendaId(Integer associateId, Integer agendaId);
    List<VoteEntity> findByAgendaId(Integer agendaId);
}
