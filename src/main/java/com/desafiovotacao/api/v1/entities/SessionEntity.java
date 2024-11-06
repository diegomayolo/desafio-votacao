package com.desafiovotacao.api.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "sessions")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    public static final int DEFAULT_MIN_DURATION = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer duration;
    
    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private AgendaEntity agenda;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @PrePersist
    public void prePersist() {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }

        endTime = startTime.plusMinutes(Objects.requireNonNullElse(duration, DEFAULT_MIN_DURATION));
    }
}
