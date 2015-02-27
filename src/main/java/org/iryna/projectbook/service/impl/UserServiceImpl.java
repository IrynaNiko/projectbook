package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.UserDao;
import org.iryna.projectbook.pojo.User;
import org.iryna.projectbook.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User checkUserByEmail(User user) {
        return userDao.checkUserByEmail(user);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<User> readAllUsers() {
        return userDao.readAllUsers();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer deleteUser(int id) {
        return userDao.deleteUser(id);
    }
}
