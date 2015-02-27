package org.iryna.projectbook.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.FileDao;
import org.iryna.projectbook.pojo.FilePB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDaoHibernateImpl implements FileDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public FilePB createFile(FilePB file) {
        Session session = sessionFactory.getCurrentSession();
        Integer id = (Integer) session.save(file);
        return (FilePB) session.load(FilePB.class, id);
    }

    @Override
    public List<FilePB> readAllFiles() {
        return (List<FilePB>) sessionFactory.getCurrentSession().createCriteria(FilePB.class).list();
    }

    @Override
    public FilePB getFileById(int id) {
        return (FilePB) sessionFactory.getCurrentSession().get(FilePB.class, id);
    }

    @Override
    public FilePB updateFile(FilePB file) {
        Session session = sessionFactory.getCurrentSession();
        session.update(file);
        return (FilePB) session.load(FilePB.class, file.getId());
    }

    @Override
    public FilePB deleteFile(int id) {
        Session session = sessionFactory.getCurrentSession();
        FilePB fileToDel = (FilePB) session.load(FilePB.class, id);
        session.delete(fileToDel);
        return fileToDel;
    }
}
