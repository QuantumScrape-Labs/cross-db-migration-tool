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
@Table(name = "migration_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MigrationJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "source_config", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> sourceConfig;

    @Column(name = "target_config", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> targetConfig;

    @Column(name = "mapping_rules", columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> mappingRules;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
