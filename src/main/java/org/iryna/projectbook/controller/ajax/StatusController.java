package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.Status;
import org.iryna.projectbook.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/pb/ajax/status")
public class StatusController {
    
    @Autowired
    private StatusService statusService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Status>> readAllStatuses() {
        List<Status> resultList = statusService.readAllStatuses();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
