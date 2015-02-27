package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.ProjectDao;
import org.iryna.projectbook.pojo.Project;
import org.iryna.projectbook.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Project createProject(Project project) {
        return projectDao.createProject(project);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Project> readAllProjects() {
        return projectDao.readAllProjects();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Project> readAllProjectsWithParams(int rows,int pageNum,String sortColumn,String sortType) {
        return projectDao.readAllProjectsWithParams(rows, pageNum, sortColumn, sortType);
    }
      
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Project readProjectById(int id) {
        return projectDao.readProjectById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Project updateProject(Project project) {
        return projectDao.updateProject(project);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer deleteProject(int id) {
        return projectDao.deleteProject(id);
    }
}
