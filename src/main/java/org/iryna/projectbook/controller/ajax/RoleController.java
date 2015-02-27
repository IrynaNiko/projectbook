package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.Role;
import org.iryna.projectbook.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/pb/ajax/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Role>> readAllRoles() {
        List<Role> resultList = roleService.readAllRoles();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

}
