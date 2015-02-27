package org.iryna.projectbook.dao.impl;

import org.hibernate.SessionFactory;
import org.iryna.projectbook.dao.RoleDao;
import org.iryna.projectbook.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoHibernateImpl implements RoleDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Role> readAllRoles() {
        return (List<Role>) sessionFactory.getCurrentSession().createCriteria(Role.class).list();
    }
}
