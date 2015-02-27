package org.iryna.projectbook.dao;

import org.iryna.projectbook.pojo.Task;
import java.util.List;

public interface TaskDao {

    Task        createTask(Task task);

    List<Task>  readAllTasks(int rows,int pageNum,String sortColumn,String sortType);

    Task        readTaskById(Integer id);

    Task        updateTask(Task task);

    Integer     deleteTask(Integer id);
}
