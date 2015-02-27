package org.iryna.projectbook.dao;

import org.iryna.projectbook.pojo.Status;

import java.util.List;

public interface StatusDao {
    List<Status>  readAllStatuses();
}
