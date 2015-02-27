package org.iryna.projectbook.dao.impl;

import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.StatusDao;
import org.iryna.projectbook.pojo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatusDaoHibernateImpl implements StatusDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Status> readAllStatuses() {
        return (List<Status>) sessionFactory.getCurrentSession().createCriteria(Status.class).list();
    }
}
