package com.bash.pmbackend.services.impl;

import com.bash.pmbackend.domain.entities.Task;
import com.bash.pmbackend.repositories.TaskRepository;
import com.bash.pmbackend.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listAllTasks(UUID taskListId) {
        // returns list of task that match the taskListId
        return taskRepository.findByTaskListId(taskListId);
    }
}
