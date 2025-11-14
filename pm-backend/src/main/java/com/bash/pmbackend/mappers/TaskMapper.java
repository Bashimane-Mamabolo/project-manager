package com.bash.pmbackend.mappers;

import com.bash.pmbackend.domain.dto.TaskDto;
import com.bash.pmbackend.domain.entities.Task;

public interface TaskMapper {
    Task fromDTO(TaskDto taskDto);
    TaskDto toDTO(Task task);
}
