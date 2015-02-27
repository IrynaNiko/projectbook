package org.iryna.projectbook.dao;

import org.iryna.projectbook.pojo.SchedulerEntry;

import java.util.List;

public interface SchedulerDao {

    SchedulerEntry        createSchedulerEntry(SchedulerEntry schEntry);

    List<SchedulerEntry>  readAllEntries();

    List<SchedulerEntry>  readAllEntriesByUserId(String userId);

    SchedulerEntry        readSchedulerEntryById(String schEntryId);

    SchedulerEntry        updateSchedulerEntry(SchedulerEntry schEntry);

    Integer               deleteSchedulerEntry(int id);
}
