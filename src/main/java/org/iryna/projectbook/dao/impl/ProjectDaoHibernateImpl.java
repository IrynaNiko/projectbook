package org.iryna.projectbook.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.ProjectDao;
import org.iryna.projectbook.pojo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDaoHibernateImpl implements ProjectDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Project createProject(Project project) {
        Session session = sessionFactory.getCurrentSession();
        int projectId = (Integer) session.save(project);
        return (Project) session.load(Project.class, projectId);
    }

    @Override
    public List<Project> readAllProjects() {
        return (List<Project>) sessionFactory.getCurrentSession().createCriteria(Project.class).list();
    }

    @Override
    public List<Project> readAllProjectsWithParams(int rows, int pageNum, String sortColumn, String sortType) {
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
            case "manager.surname": dbNameSortColumn = "manager.surname";
                break;
            default: dbNameSortColumn = "date_creation";
        }

        return (List<Project>) sessionFactory.getCurrentSession().createQuery(
                new String("FROM Project ORDER BY " + dbNameSortColumn + " " + sortType))
                .setFirstResult(pageNum*rows - rows)
                .setMaxResults(pageNum*rows).list();
    }

    @Override
    public Project readProjectById(int id) {
        return (Project) sessionFactory.getCurrentSession().get(Project.class, id);
    }

    @Override
    public Project updateProject(Project project) {
        Session session = sessionFactory.getCurrentSession();
        session.update(project);
        return (Project) session.load(Project.class, project.getId());
    }

    @Override
    public Integer deleteProject(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(Project.class, id));
        return id;
    }
}
