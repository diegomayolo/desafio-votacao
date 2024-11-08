package com.desafiovotacao.api.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "agendas")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String title;
    private String description;
}
