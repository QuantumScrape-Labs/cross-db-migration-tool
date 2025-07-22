package com.quantumscrapelabs.databasemigration.entity;

import com.quantumscrapelabs.databasemigration.util.JsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "job_executions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private MigrationJob job;

    @Column(nullable = false)
    private String status; // CREATED, RUNNING, COMPLETED, FAILED

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "executed_by", nullable = false)
    private String executedBy;

    @Column(name = "execution_stats", columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> executionStats;
}
