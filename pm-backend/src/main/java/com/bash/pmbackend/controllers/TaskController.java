package com.bash.pmbackend.controllers;

import com.bash.pmbackend.domain.dto.TaskDto;
import com.bash.pmbackend.domain.entities.Task;
import com.bash.pmbackend.mappers.TaskMapper;
import com.bash.pmbackend.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-list/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping()
    public List<TaskDto> listAllTasks(
            @PathVariable("task_list_id") UUID taskListId
    ) {
       return taskService.listAllTasks(taskListId)
               .stream()
               .map(taskMapper::toDTO)
               .toList();
    }

    @PostMapping
    public TaskDto createTask(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskDto taskDto) {
        return taskMapper.toDTO(taskService.createTask(taskListId,
                taskMapper.fromDTO(taskDto)));
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId) {

        return taskService.getTask(taskListId, taskId)
                .map(taskMapper::toDTO);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto
    ) {
        Task updatedTask = taskService.updateTask(
                taskListId,
                taskId,
                taskMapper.fromDTO(taskDto)
        );
        return taskMapper.toDTO(updatedTask);
    }

}
