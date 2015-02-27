package org.iryna.projectbook.dao.impl;

import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.PriorityDao;
import org.iryna.projectbook.pojo.Priority;
import org.iryna.projectbook.pojo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriorityDaoHibernateImpl implements PriorityDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Priority> readAllPriorities() {
        return (List<Priority>) sessionFactory.getCurrentSession().createCriteria(Priority.class).list();
    }
}
