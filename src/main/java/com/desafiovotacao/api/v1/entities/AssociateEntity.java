package com.desafiovotacao.api.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

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
    
    private String name;
    private String cpf;
    
    @CreationTimestamp
    private Instant createdAt;
}
