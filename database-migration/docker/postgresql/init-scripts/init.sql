CREATE TABLE migration_jobs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    source_config JSONB NOT NULL,
    target_config JSONB NOT NULL,
    mapping_rules JSONB,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE job_executions (
    id BIGSERIAL PRIMARY KEY,
    job_id BIGINT NOT NULL REFERENCES migration_jobs(id),
    status VARCHAR(50) NOT NULL,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    executed_by VARCHAR(255) NOT NULL,
    execution_stats JSONB
);

CREATE INDEX idx_job_executions_job_id ON job_executions(job_id);