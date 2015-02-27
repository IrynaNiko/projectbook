package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.AnnouncementDao;
import org.iryna.projectbook.pojo.Announcement;
import org.iryna.projectbook.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public Announcement createAnnouncement(Announcement announcement) {
        return announcementDao.createAnnouncement(announcement);
    }

    @Override
    public List<Announcement> readAllAnnouncements(String rowsAmount) {
        return announcementDao.readAllAnnouncements(rowsAmount) ;
    }

    @Override
    public Announcement readAnnouncementById(int id) {
        return announcementDao.readAnnouncementById(id);
    }

    @Override
    public Announcement updateAnnouncement(Announcement announcement) {
        return announcementDao.updateAnnouncement(announcement);
    }

    @Override
    public Integer deleteAnnouncement(int id) {
        return announcementDao.deleteAnnouncement(id);
    }
}
