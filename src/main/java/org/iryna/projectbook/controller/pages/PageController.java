package org.iryna.projectbook.controller.pages;

import org.iryna.projectbook.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {

    @RequestMapping(method = RequestMethod.GET, value = "/project")
    public ModelAndView showProjectPage(@RequestParam(value = "firstlogin", required = false) Boolean isFirstLogin,
                                        HttpServletRequest request, HttpServletResponse response ) {
        User curUser = (User)request.getSession().getAttribute("user");

        if(isFirstLogin != null && isFirstLogin){
            response.addCookie(new Cookie("userId",Integer.toString(curUser.getId())));
        }

        ModelAndView model = new ModelAndView();
        model.addObject("userName", curUser.getName());
        model.addObject("userSurname", curUser.getSurname());
        model.setViewName("project");
        return model;
    }

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public ModelAndView loginPage(
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("index");
        return model;
    }

    /*@RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is protected page - Admin Page!");
        model.setViewName("admin");

        return model;

    }*/
}
