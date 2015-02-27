package org.iryna.projectbook.service;

import org.iryna.projectbook.pojo.Announcement;
import java.util.List;

public interface AnnouncementService {

    Announcement        createAnnouncement(Announcement announcement);

    List<Announcement>  readAllAnnouncements(String rowsAmount);

    Announcement        readAnnouncementById(int id);

    Announcement        updateAnnouncement(Announcement announcement);

    Integer             deleteAnnouncement(int id);
}
