package com.bash.pmbackend.services.impl;

import com.bash.pmbackend.domain.entities.TaskList;
import com.bash.pmbackend.repositories.TaskListRepository;
import com.bash.pmbackend.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    LocalDateTime now = LocalDateTime.now();
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {

        if (taskList.getId() != null) {
            throw new IllegalArgumentException("Task list already has an ID");
        }

        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title is required");
        }
        return taskListRepository.save(new TaskList(
               null,
               taskList.getTitle(),
               taskList.getDescription(),
               null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskListById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Task list id is required");
        }
        return taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        // Validations
        if (taskList.getTasks() == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }
        if (!Objects.equals(taskList.getId(), id)) {
            throw new IllegalArgumentException("Use correct id to modify task list");
        }
        TaskList dbTaskList = taskListRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Task list id does not exist"));

        // Fill and save to database
        dbTaskList.setTitle(taskList.getTitle());
        dbTaskList.setDescription(taskList.getDescription());
        dbTaskList.setUpdatedDate(LocalDateTime.now());

        return taskListRepository.save(dbTaskList);

    }


}
