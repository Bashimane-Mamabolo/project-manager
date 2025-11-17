package com.bash.pmbackend.services;

import com.bash.pmbackend.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    // Get the list of tasks that belong to a specific TaskList
    List<Task> listAllTasks(UUID taskListId);

}
