package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.*;
import org.iryna.projectbook.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/pb/ajax/task")
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = taskService.createTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Task>> readAllTasks(
            @RequestParam(value = "search", required = false) Boolean search,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "page", required = false) Integer pageNum,
            @RequestParam(value = "sidx", required = false) String sortColumn,
            @RequestParam(value = "sord", required = false) String sortType) {
        List<Task> resultList = taskService.readAllTasks(rows, pageNum, sortColumn, sortType);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Task> readTaskById(@PathVariable Integer id){
        Task receivedTask = taskService.readTaskById(id);
        return new ResponseEntity<>(receivedTask, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        Task updatedTask = taskService.updateTask(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteTaskById(@PathVariable Integer id){
        Integer deleteStatus = taskService.deleteTask(id);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteMultipleTasks(@RequestBody Integer taskArrId[]){
        for (Integer i :  taskArrId){
            taskService.deleteTask(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
