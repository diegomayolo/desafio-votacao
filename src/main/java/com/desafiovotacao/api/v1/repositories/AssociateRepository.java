package com.desafiovotacao.api.v1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafiovotacao.api.v1.entities.AssociateEntity;

public interface AssociateRepository extends JpaRepository<AssociateEntity, Integer>
{
    Optional<AssociateEntity> findByCpf( String cpf);
}
