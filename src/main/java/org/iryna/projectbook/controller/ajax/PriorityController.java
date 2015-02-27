package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.Priority;
import org.iryna.projectbook.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/pb/ajax/priority")
public class PriorityController {
    
    @Autowired
    private PriorityService statusService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Priority>> readAllStatuses() {
        List<Priority> resultList = statusService.readAllPriorities();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
