package com.bash.pmbackend.services.impl;

import com.bash.pmbackend.domain.entities.Task;
import com.bash.pmbackend.domain.entities.TaskList;
import com.bash.pmbackend.domain.entities.TaskPriority;
import com.bash.pmbackend.domain.entities.TaskStatus;
import com.bash.pmbackend.repositories.TaskListRepository;
import com.bash.pmbackend.repositories.TaskRepository;
import com.bash.pmbackend.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    @Transactional
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

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }
    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID is required");
        }
        if (!Objects.equals(task.getId(), taskId)) {
            throw new IllegalArgumentException("Task IDs do not match");
        }
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority is required");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status is required");
        }
        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("Task Due date is required");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task list ID not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdatedDate(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
