package org.iryna.projectbook.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.iryna.projectbook.dao.SchedulerDao;
import org.iryna.projectbook.pojo.SchedulerEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchedulerDaoHibernateImpl implements SchedulerDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public SchedulerEntry createSchedulerEntry(SchedulerEntry schEntry) {
        Session session = sessionFactory.getCurrentSession();
        int scEntryId = (Integer) session.save(schEntry);
        return (SchedulerEntry) session.load(SchedulerEntry.class, scEntryId);
    }

    @Override
    public List<SchedulerEntry> readAllEntries() {
        return (List<SchedulerEntry>) sessionFactory.getCurrentSession().createCriteria(SchedulerEntry.class).list();
    }

    @Override
    public List<SchedulerEntry> readAllEntriesByUserId(String userId) {
        return (List<SchedulerEntry>) sessionFactory.getCurrentSession()
                .createCriteria(SchedulerEntry.class)
                .add(Restrictions.gt("user_id", userId))
                .list();
    }

    @Override
    public SchedulerEntry readSchedulerEntryById(String schEntryId) {
        return (SchedulerEntry) sessionFactory.getCurrentSession().get(SchedulerEntry.class, schEntryId);
    }

    @Override
    public SchedulerEntry updateSchedulerEntry(SchedulerEntry schEntry) {
        Session session = sessionFactory.getCurrentSession();
        session.update(schEntry);
        return (SchedulerEntry) session.load(SchedulerEntry.class, schEntry.getId());
    }

    @Override
    public Integer deleteSchedulerEntry(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(SchedulerEntry.class, id));
        return id;
    }
}
