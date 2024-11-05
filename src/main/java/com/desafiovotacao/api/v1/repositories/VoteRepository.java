package com.desafiovotacao.api.v1.repositories;

import com.desafiovotacao.api.v1.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {
}
