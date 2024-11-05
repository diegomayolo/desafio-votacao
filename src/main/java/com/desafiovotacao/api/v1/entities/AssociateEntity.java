package com.desafiovotacao.api.v1.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "associate")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Preencha o campo 'name'")
    private String name;
    
    @NotBlank(message = "Preencha o campo 'cpf'")
    private String cpf;
    
    @CreationTimestamp
    private Instant createdAt;
}
