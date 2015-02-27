package org.iryna.projectbook.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.iryna.projectbook.dao.CompanyFilesDao;
import org.iryna.projectbook.pojo.CompanyFilesSection;
import org.iryna.projectbook.pojo.FilePB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyFilesDaoHibernateImpl implements CompanyFilesDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public CompanyFilesSection createSection(CompanyFilesSection section) {
        Session session = sessionFactory.getCurrentSession();
        int annId = (Integer) session.save(section);
        return (CompanyFilesSection) session.load(CompanyFilesSection.class, annId);
    }

    @Override
    public List<CompanyFilesSection> readAllSections() {
        return (List<CompanyFilesSection>)sessionFactory.getCurrentSession()
                .createCriteria(CompanyFilesSection.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void saveFileBySection(int id, FilePB newFile) {
        CompanyFilesSection cf = (CompanyFilesSection) sessionFactory.getCurrentSession().get(CompanyFilesSection.class, id);
        cf.getFileList().add(newFile);
        sessionFactory.getCurrentSession().save(cf);
    }

    @Override
    public CompanyFilesSection readSectionById(int id) {
        return  (CompanyFilesSection) sessionFactory.getCurrentSession().get(CompanyFilesSection.class, id);
    }

    @Override
    public CompanyFilesSection updateSection(CompanyFilesSection section) {
        Session session = sessionFactory.getCurrentSession();
        session.update(section);
        return (CompanyFilesSection) session.get(CompanyFilesSection.class, section.getId());
    }

    @Override
    public Integer deleteSection(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(CompanyFilesSection.class, id));
        return id;
    }
}
