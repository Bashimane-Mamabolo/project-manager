package com.bash.pmbackend.controllers;

import com.bash.pmbackend.domain.dto.TaskDto;
import com.bash.pmbackend.mappers.TaskMapper;
import com.bash.pmbackend.services.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

}
