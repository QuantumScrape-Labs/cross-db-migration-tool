package com.quantumscrapelabs.databasemigration.repository;

import com.quantumscrapelabs.databasemigration.entity.MigrationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MigrationJobRepository extends JpaRepository<MigrationJob,Long> {
    List<MigrationJob> findByActive(boolean active);
}
