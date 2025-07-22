package com.quantumscrapelabs.databasemigration.controller;

import com.quantumscrapelabs.databasemigration.dto.migrationjob.CreateJobRequestDto;
import com.quantumscrapelabs.databasemigration.entity.JobExecution;
import com.quantumscrapelabs.databasemigration.entity.MigrationJob;
import com.quantumscrapelabs.databasemigration.exception.ResourceNotFoundException;
import com.quantumscrapelabs.databasemigration.service.ConfigurationStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/migration-jobs")
@RequiredArgsConstructor
public class MigrationJobController {
    private final ConfigurationStoreService configService;

//    public MigrationJobController(ConfigurationStoreService configService) {
//        this.configService = configService;
//    }

    @PostMapping
    public MigrationJob createJob(@RequestBody CreateJobRequestDto request) {
        return configService.createMigrationJob(request);
    }

    @GetMapping("/{id}")
    public MigrationJob getJob(@PathVariable Long id) {
        return configService.getJobById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }

    @GetMapping
    public List<MigrationJob> getAllActiveJobs() {
        return configService.getAllActiveJobs();
    }

    @PostMapping("/{jobId}/executions")
    public JobExecution startExecution(@PathVariable Long jobId,
                                       @RequestParam String executedBy) {
        return configService.startJobExecution(jobId, executedBy);
    }

    @GetMapping("/{jobId}/executions")
    public List<JobExecution> getExecutions(@PathVariable Long jobId) {
        return configService.getJobExecutions(jobId);
    }

    @PutMapping("/executions/{executionId}/status")
    public void updateStatus(@PathVariable Long executionId,
                             @RequestParam String status) {
        configService.updateJobExecutionStatus(executionId, status);
    }

    @PutMapping("/executions/{executionId}/stats")
    public void updateStats(@PathVariable Long executionId,
                            @RequestBody Map<String, Object> stats) {
        configService.updateExecutionStats(executionId, stats);
    }

}
