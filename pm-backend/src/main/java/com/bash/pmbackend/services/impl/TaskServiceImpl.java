package com.bash.pmbackend.services.impl;

import com.bash.pmbackend.domain.entities.Task;
import com.bash.pmbackend.domain.entities.TaskList;
import com.bash.pmbackend.domain.entities.TaskPriority;
import com.bash.pmbackend.domain.entities.TaskStatus;
import com.bash.pmbackend.repositories.TaskListRepository;
import com.bash.pmbackend.repositories.TaskRepository;
import com.bash.pmbackend.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listAllTasks(UUID taskListId) {
        // returns list of task that match the taskListId
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already exists and has an ID");
        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title is required");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList= taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list ID not found"));
        LocalDateTime now = LocalDateTime.now();
        Task newTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(newTask);


    }
}
