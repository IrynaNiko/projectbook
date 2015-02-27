package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.RoleDao;
import org.iryna.projectbook.pojo.Role;
import org.iryna.projectbook.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Role> readAllRoles() {
        return roleDao.readAllRoles();
    }
}
