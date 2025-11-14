package com.bash.pmbackend.services;

import com.bash.pmbackend.domain.entities.TaskList;


import java.util.List;


public interface TaskListService {

    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);

}
