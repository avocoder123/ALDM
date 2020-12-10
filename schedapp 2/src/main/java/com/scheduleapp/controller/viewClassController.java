package com.scheduleapp.controller;

import com.scheduleapp.model.classes;
import com.scheduleapp.model.users;
import com.scheduleapp.service.ClassesService;
import com.scheduleapp.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class viewClassController {

    private final UsersService userService;
    private final ClassesService classService;

    public viewClassController(UsersService userService, ClassesService classesService){
        this.classService = classesService;
        this.userService = userService;
    }

    @GetMapping("/viewClass/{class_ID}")
    public String viewClass(Model userslist, @PathVariable String class_ID){
        List<users> userlist = userService.getStudents(class_ID);
        classes theclass = classService.getTheClass(class_ID);
        userslist.addAttribute("userlist", userlist);
        userslist.addAttribute("theclass", theclass);
        return "viewClass";
    }

    @GetMapping("/viewClass/d/{class_ID}/{SID}")
    public String delStudentList(@PathVariable String class_ID, @PathVariable String SID){
        classService.removeStudent(SID, class_ID);
        userService.removeClass(SID, class_ID);
        return "redirect:/viewClass/" + class_ID;
    }

    @PostMapping("/viewClass/a")
    public String addStudentList(@RequestBody String info){
        String[] infotokens = info.split("&");
        String SID = infotokens[1].substring(4);
        String theclass = infotokens[0].substring(9);
        if(userService.checkUserExists(SID)){
            if(userService.addCourseOverride(SID, theclass)){
                classService.addUser(SID, theclass);
            }
        }
        return "redirect:/viewClass/" + theclass;
    }
}
