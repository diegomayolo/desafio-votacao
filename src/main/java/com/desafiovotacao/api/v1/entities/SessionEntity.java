package com.desafiovotacao.api.v1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    private Integer agendaId;
    private Integer duration;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @PrePersist
    public void prePersist() {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }

        endTime = startTime.plusMinutes(Objects.requireNonNullElse(duration, DEFAULT_MIN_DURATION));
    }
}
