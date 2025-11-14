package com.bash.pmbackend.mappers;

import com.bash.pmbackend.domain.dto.TaskListDto;
import com.bash.pmbackend.domain.entities.Task;
import com.bash.pmbackend.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
