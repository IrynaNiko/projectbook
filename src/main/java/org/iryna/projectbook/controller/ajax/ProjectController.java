package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.Project;
import org.iryna.projectbook.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/pb/ajax/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project newProject = projectService.createProject(project);
        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<Project>> readAllProjects() {
        return new ResponseEntity<>(projectService.readAllProjects(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Project>> readAllProjectsWithParams(
            @RequestParam(value = "search", required = false) Boolean search,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "page", required = false) Integer pageNum,
            @RequestParam(value = "sidx", required = false) String sortColumn,
            @RequestParam(value = "sord", required = false) String sortType) {

        List<Project> resultList = projectService.readAllProjectsWithParams(rows, pageNum, sortColumn, sortType);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Project> readProjectById(@PathVariable Integer id){
        Project receivedProject = projectService.readProjectById(id);
        return new ResponseEntity<>(receivedProject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        Project updatedProject = projectService.updateProject(project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteProjectById(@PathVariable Integer id){
        Integer deleteStatus = projectService.deleteProject(id);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteMultipleProjects(@RequestBody Integer projArrId[]){
        for (Integer i :  projArrId){
            projectService.deleteProject(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
