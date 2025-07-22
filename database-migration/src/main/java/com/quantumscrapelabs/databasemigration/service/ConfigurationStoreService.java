package com.quantumscrapelabs.databasemigration.service;

import com.quantumscrapelabs.databasemigration.dto.migrationjob.CreateJobRequestDto;
import com.quantumscrapelabs.databasemigration.entity.JobExecution;
import com.quantumscrapelabs.databasemigration.entity.MigrationJob;
import com.quantumscrapelabs.databasemigration.repository.JobExecutionRepository;
import com.quantumscrapelabs.databasemigration.repository.MigrationJobRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigurationStoreService {
    private final MigrationJobRepository jobRepository;
    private final JobExecutionRepository executionRepository;


//    public ConfigurationStoreService(MigrationJobRepository jobRepository,
//                                     JobExecutionRepository executionRepository) {
//        this.jobRepository = jobRepository;
//        this.executionRepository = executionRepository;
//    }

    @Transactional
    public MigrationJob createMigrationJob(CreateJobRequestDto createJobDto) {

        MigrationJob job = new MigrationJob();
        job.setName(createJobDto.name());
        job.setDescription(createJobDto.description());
        job.setCreatedBy(createJobDto.createdBy());
        job.setCreatedAt(Instant.now());
        job.setSourceConfig(createJobDto.sourceConfig());
        job.setTargetConfig(createJobDto.targetConfig());
        job.setMappingRules(createJobDto.mappingRules());
        job.setActive(true);

        return jobRepository.save(job);
    }

    public Optional<MigrationJob> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public List<MigrationJob> getAllActiveJobs() {
        return jobRepository.findByActive(true);
    }

    @Transactional
    public JobExecution startJobExecution(Long jobId, String executedBy) {
        MigrationJob job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        JobExecution execution = new JobExecution();
        execution.setJob(job);
        execution.setStatus("CREATED");
        execution.setExecutedBy(executedBy);
        execution.setStartTime(Instant.now());

        return executionRepository.save(execution);
    }

    @Transactional
    public void updateJobExecutionStatus(Long executionId, String status) {
        JobExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found"));

        execution.setStatus(status);
        if ("COMPLETED".equals(status) || "FAILED".equals(status)) {
            execution.setEndTime(Instant.now());
        }
        executionRepository.save(execution);
    }

    @Transactional
    public void updateExecutionStats(Long executionId, Map<String, Object> stats) {
        JobExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found"));

        execution.setExecutionStats(stats);
        executionRepository.save(execution);
    }

    public List<JobExecution> getJobExecutions(Long jobId) {
        return executionRepository.findByJobIdOrderByStartTimeDesc(jobId);
    }

}
