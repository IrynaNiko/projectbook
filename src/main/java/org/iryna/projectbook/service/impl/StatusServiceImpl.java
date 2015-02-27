package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.StatusDao;
import org.iryna.projectbook.pojo.Status;
import org.iryna.projectbook.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusDao statusDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Status> readAllStatuses() {
        return statusDao.readAllStatuses();
    }
}
