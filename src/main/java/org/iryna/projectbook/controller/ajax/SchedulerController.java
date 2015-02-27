package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.SchedulerEntry;
import org.iryna.projectbook.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pb/ajax/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SchedulerEntry> createSchedulerEntry(@RequestBody SchedulerEntry schedulerEntry) {
        SchedulerEntry newSchedulerEntry = schedulerService.createSchedulerEntry(schedulerEntry);
        return new ResponseEntity<>(newSchedulerEntry, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SchedulerEntry>> readAllSchedulerEntries() {
        List<SchedulerEntry> resultList = schedulerService.readAllEntries();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<List<SchedulerEntry>> readAllEntriesByUserId(@PathVariable String usedId,
                                             @RequestParam(value = "start", required = false) Date dateStart,
                                             @RequestParam(value = "end", required = false) Date dateEnd){
        List<SchedulerEntry> resultList = schedulerService.readAllEntriesByUserId(usedId);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/entry/{id}")
    public ResponseEntity<SchedulerEntry> readSchedulerEntryById(@PathVariable String id){
        SchedulerEntry receivedSchedulerEntry = schedulerService.readSchedulerEntryById(id);
        return new ResponseEntity<>(receivedSchedulerEntry, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchedulerEntry> updateSchedulerEntry(@RequestBody SchedulerEntry schedulerEntry) {
        SchedulerEntry updatedSchedulerEntry = schedulerService.updateSchedulerEntry(schedulerEntry);
        return new ResponseEntity<>(updatedSchedulerEntry, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteSchedulerEntryById(@PathVariable Integer id){
        Integer deleteStatus = schedulerService.deleteSchedulerEntry(id);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }
}
