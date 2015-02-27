package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.*;
import org.iryna.projectbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pb/ajax/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.POST)
     public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<User> loginUser(@RequestBody User user, HttpServletRequest request) throws IOException {

        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute("user", userService.getUserByEmail(user.getEmail()));

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> readAllUsers() {
        List<User> resultList = userService.readAllUsers();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        User receivedUser = userService.getUserById(id);
        return new ResponseEntity<>(receivedUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteUserById(@PathVariable Integer id){
        Integer deleteStatus = userService.deleteUser(id);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "param")
    public @ResponseBody User getUserByEmail(@RequestParam(value = "email", required = true) String email){
        return userService.getUserByEmail(email);
    }
}
