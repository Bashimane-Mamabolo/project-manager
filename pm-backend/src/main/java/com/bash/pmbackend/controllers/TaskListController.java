package com.bash.pmbackend.controllers;

import com.bash.pmbackend.domain.dto.TaskListDto;
import com.bash.pmbackend.domain.entities.TaskList;
import com.bash.pmbackend.mappers.TaskListMapper;
import com.bash.pmbackend.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    // Dependencies (service and dto mapper)
    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    // Dependency constructor injection
    public TaskListController(TaskListService taskListService,
                              TaskListMapper taskListMapper
    ) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    // Controller actions
    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(
            @RequestBody TaskListDto taskListDto
    ) {
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );
        return taskListMapper.toDto(createdTaskList);
    }
}
