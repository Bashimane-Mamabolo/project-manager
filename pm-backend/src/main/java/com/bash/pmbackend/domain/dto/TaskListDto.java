package com.bash.pmbackend.domain.dto;

import java.util.List;
import java.util.UUID;
// No JPA annotations
// No relationship management
// No data timestamps fields
public record TaskListDto(
        UUID id,
        String title,
        String description,
        // Computed fields
        Integer count, // num of tasks in our list
        Double progress, // A num between 0.0 and 1.0 of the completed tasks
        List<TaskDto> tasks // Use DTO instead of the entity object
) {
}
