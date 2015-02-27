package org.iryna.projectbook.service;

import org.iryna.projectbook.pojo.SchedulerEntry;

import java.util.List;

public interface SchedulerService {

    SchedulerEntry          createSchedulerEntry(SchedulerEntry schEntry);

    List<SchedulerEntry>    readAllEntries();

    List<SchedulerEntry>    readAllEntriesByUserId(String userId);

    SchedulerEntry          readSchedulerEntryById(String schEntryId);

    SchedulerEntry          updateSchedulerEntry(SchedulerEntry schEntry);

    Integer                 deleteSchedulerEntry(int id);
}
