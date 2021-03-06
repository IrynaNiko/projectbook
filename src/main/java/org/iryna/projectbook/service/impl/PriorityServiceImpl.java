package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.PriorityDao;
import org.iryna.projectbook.pojo.Priority;
import org.iryna.projectbook.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PriorityServiceImpl implements PriorityService {

    @Autowired
    private PriorityDao priorityDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Priority> readAllPriorities() {
        return priorityDao.readAllPriorities();
    }
}
