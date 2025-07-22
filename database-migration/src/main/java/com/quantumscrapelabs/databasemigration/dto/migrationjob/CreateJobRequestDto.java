package com.quantumscrapelabs.databasemigration.dto.migrationjob;

import java.util.Map;

public record CreateJobRequestDto(String name,
                                  String description,
                                  String createdBy,
                                  Map<String, Object> sourceConfig,
                                  Map<String, Object> targetConfig,
                                  Map<String, Object> mappingRules) {
}
