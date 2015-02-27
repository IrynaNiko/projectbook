package org.iryna.projectbook.dao;

import org.iryna.projectbook.pojo.Announcement;

import java.util.List;

public interface AnnouncementDao {

    Announcement        createAnnouncement(Announcement announcement);

    List<Announcement>  readAllAnnouncements(String rowsAmount);

    Announcement        readAnnouncementById(int id);

    Announcement        updateAnnouncement(Announcement announcement);

    Integer             deleteAnnouncement(int id);
}
