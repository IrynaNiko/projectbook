package org.iryna.projectbook.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.iryna.projectbook.dao.UserDao;
import org.iryna.projectbook.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    private static final Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Override
    public User createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        user.setPassword(encoder.encodePassword(user.getPassword(), null));
        Integer userId = (Integer) session.save(user);
        return (User) session.load(User.class, userId);
    }

    @Override
    public User checkUserByEmail(User user) {
        return (User) sessionFactory.getCurrentSession().createCriteria( User.class )
                .add( Restrictions.eq("email", user.getEmail()) )
                .uniqueResult();
    }

    @Override
    public User getUserByEmail(String email) {
        User user = (User) sessionFactory.getCurrentSession().createCriteria( User.class )
                    .add( Restrictions.eq("email", email) )
                    .uniqueResult();
        return user;
    }

    @Override
    public List<User> readAllUsers() {
        return (List<User>) sessionFactory.getCurrentSession().createQuery(
                new String("FROM User ORDER BY surname asc")).list();
    }

    @Override
    public User getUserById(int id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User updateUser(User user) {
        int userId = user.getId();
        Session session = sessionFactory.getCurrentSession();
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            User oldUser = getUserById(userId);
            user.setPassword(oldUser.getPassword());
            session.evict(oldUser);
        } else {
            user.setPassword(encoder.encodePassword(user.getPassword(), null));
        }

        session.update(user);
        return (User) session.load(User.class, userId);
    }

    @Override
    public Integer deleteUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(User.class, id));
        return id;
    }
}
