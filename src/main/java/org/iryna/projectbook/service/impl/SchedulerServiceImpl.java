package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.SchedulerDao;
import org.iryna.projectbook.pojo.SchedulerEntry;
import org.iryna.projectbook.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerDao schedulerDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SchedulerEntry createSchedulerEntry(SchedulerEntry schEntry) {
        return schedulerDao.createSchedulerEntry(schEntry);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SchedulerEntry> readAllEntries() {
        return schedulerDao.readAllEntries();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SchedulerEntry> readAllEntriesByUserId(String userId) {
        return schedulerDao.readAllEntriesByUserId(userId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SchedulerEntry readSchedulerEntryById(String schEntryId) {
        return schedulerDao.readSchedulerEntryById(schEntryId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SchedulerEntry updateSchedulerEntry(SchedulerEntry schEntry) {
        return schedulerDao.updateSchedulerEntry(schEntry);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer deleteSchedulerEntry(int id) {
        return schedulerDao.deleteSchedulerEntry(id);
    }
}
