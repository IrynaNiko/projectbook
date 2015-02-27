package org.iryna.projectbook.service;

import org.iryna.projectbook.pojo.Project;

import java.util.List;

public interface ProjectService {

    Project         createProject(Project project);

    List<Project>   readAllProjects();

    List<Project>   readAllProjectsWithParams(int rows,int pageNum,String sortColumn,String sortType);

    Project         readProjectById(int id);

    Project         updateProject(Project project);

    Integer         deleteProject(int id);

}
