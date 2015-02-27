package org.iryna.projectbook.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.AnnouncementDao;
import org.iryna.projectbook.pojo.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AnnouncementDaoHibernateImpl implements AnnouncementDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Announcement createAnnouncement(Announcement announcement) {
        Session session = sessionFactory.getCurrentSession();
        int annId = (Integer) session.save(announcement);
        return (Announcement) session.load(Announcement.class, annId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Announcement> readAllAnnouncements(String rowsAmount) {
        return (List<Announcement>) sessionFactory.getCurrentSession()
                .createQuery(new String("FROM Announcement ORDER BY date_creation DESC"))
                .setMaxResults(Integer.parseInt(rowsAmount)).list();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Announcement readAnnouncementById(int id) {
        return (Announcement) sessionFactory.getCurrentSession().get(Announcement.class, id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Announcement updateAnnouncement(Announcement announcement) {
        Session session = sessionFactory.getCurrentSession();
        session.update(announcement);
        return (Announcement) session.get(Announcement.class, announcement.getId());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer deleteAnnouncement(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(Announcement.class, id));
        return id;
    }
}
