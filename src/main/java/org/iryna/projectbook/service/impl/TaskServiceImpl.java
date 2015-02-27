package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.TaskDao;
import org.iryna.projectbook.pojo.Task;
import org.iryna.projectbook.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Task createTask(Task task) {
        return taskDao.createTask(task);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Task> readAllTasks(int rows,int pageNum,String sortColumn,String sortType){
        return taskDao.readAllTasks(rows, pageNum, sortColumn, sortType);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Task readTaskById(Integer id) {
        return taskDao.readTaskById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Task updateTask(Task task) {
        return taskDao.updateTask(task);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer deleteTask(Integer id) {
        return taskDao.deleteTask(id);
    }
}
