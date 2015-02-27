package org.iryna.projectbook.dao.impl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.TaskDao;
import org.iryna.projectbook.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TaskDaoHibernateImpl implements TaskDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Task createTask(Task task) {
        Session session = sessionFactory.getCurrentSession();
        int taskId = (Integer) session.save(task);
        return (Task) session.load(Task.class, taskId);
    }

    @Override
    public List<Task> readAllTasks(int rows,int pageNum,String sortColumn,String sortType) {

        String dbNameSortColumn;
        switch(sortColumn){
            case "Created": dbNameSortColumn = "date_creation";
                break;
            case "Started": dbNameSortColumn = "date_started";
                break;
            case "Deadline": dbNameSortColumn = "date_deadline";
                break;
            case "Finished": dbNameSortColumn = "date_finished";
                break;
            case "status.name": dbNameSortColumn = "status.name";
                break;
            case "priority.name": dbNameSortColumn = "priority.name";
                break;
            case "manager.surname": dbNameSortColumn = "manager.surname";
                break;
            case "executive.surname": dbNameSortColumn = "executive.surname";
                break;
            case "project.name": dbNameSortColumn = "project.name";
                break;
            default: dbNameSortColumn = "date_creation";
        }

        return (List<Task>) sessionFactory.getCurrentSession().createQuery(
                new String("FROM Task ORDER BY " + dbNameSortColumn + " " + sortType))
                .setFirstResult(pageNum*rows - rows)
                .setMaxResults(pageNum*rows).list();
    }

    @Override
    public Task readTaskById(Integer id) {
        return (Task) sessionFactory.getCurrentSession().get(Task.class, id);
    }

    @Override
    public Task updateTask(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.update(task);
        return (Task) session.load(Task.class, task.getId());
    }

    @Override
    public Integer deleteTask(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(Task.class, id));
        return id;
    }
}
