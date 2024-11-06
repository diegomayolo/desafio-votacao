package com.desafiovotacao.api.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(
        name = "votes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"associate_id", "agenda_id"})
        }
)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean vote;
    
    @ManyToOne
    @JoinColumn(name = "associate_id")
    private AssociateEntity associate;

    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private AgendaEntity agenda;
}
