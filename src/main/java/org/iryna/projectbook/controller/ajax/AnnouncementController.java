package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.*;
import org.iryna.projectbook.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pb/ajax/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
        Announcement newAnnouncement = announcementService.createAnnouncement(announcement);
        return new ResponseEntity<>(newAnnouncement, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Announcement>> readAllAnnouncements(@RequestParam(value = "rows", required = true) String rowsAmount) {
        List<Announcement> resultList = announcementService.readAllAnnouncements(rowsAmount);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Announcement> readAnnouncementById(@PathVariable Integer id){
        Announcement receivedAnnouncement = announcementService.readAnnouncementById(id);
        return new ResponseEntity<>(receivedAnnouncement, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Announcement> updateAnnouncement(@RequestBody Announcement announcement) {
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcement);
        return new ResponseEntity<>(updatedAnnouncement, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteAnnouncementById(@PathVariable Integer id){
        int deleteStatus = announcementService.deleteAnnouncement(id);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }

    /*@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteMultipleAnnouncement(@RequestBody Integer annArrId[]){
        for (Integer i :  annArrId){
            announcementService.deleteAnnouncement(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
