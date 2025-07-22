package com.quantumscrapelabs.databasemigration.repository;

import com.quantumscrapelabs.databasemigration.entity.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobExecutionRepository extends JpaRepository<JobExecution,Long> {
    List<JobExecution> findByJobIdOrderByStartTimeDesc(Long jobId);
}
