package com.scheduleapp.controller;

import com.scheduleapp.model.classes;
import com.scheduleapp.service.ClassesService;
import com.scheduleapp.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ScheduleDisplayController {

    private final UsersService userService;
    private final ClassesService classService;


    public ScheduleDisplayController(UsersService userService, ClassesService classesService){
        this.classService = classesService;
        this.userService = userService;
    }

    //for now hardcoded 00000000, later will add parameter for current user
    @GetMapping("/scheduleDisplay")
    public String schedule(Model model){
        List<classes> schedule = userService.getSchedule("00000000");
        model.addAttribute("schedule",schedule);
        return "scheduleDisplay";
    }

    //will add parameter for user later
    @GetMapping("/drop/{course_ID}")
    public String dropClass(Model model, @PathVariable String course_ID){
        classService.removeStudent("00000000", course_ID);
        userService.removeClass("00000000",course_ID);
        return "redirect:/scheduleDisplay";
    }

}
