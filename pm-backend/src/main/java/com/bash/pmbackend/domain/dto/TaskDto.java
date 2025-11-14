package com.bash.pmbackend.domain.dto;

import com.bash.pmbackend.domain.entities.Task;
import com.bash.pmbackend.domain.entities.TaskPriority;
import com.bash.pmbackend.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
