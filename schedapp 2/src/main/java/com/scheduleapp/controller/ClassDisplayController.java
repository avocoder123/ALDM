package com.scheduleapp.controller;

import com.scheduleapp.model.classes;
import com.scheduleapp.model.users;
import com.scheduleapp.service.ClassesService;
import com.scheduleapp.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class ClassDisplayController {

    private final UsersService userService;
    private final ClassesService classService;

    public ClassDisplayController(UsersService userService, ClassesService classesService){
        this.classService = classesService;
        this.userService = userService;
    }

    @GetMapping("/classDisplay")
    public String CourseDisplay(Model model) {
        model.addAttribute("courses", classService.getClasses());
        return "classDisplay";
    }

    @GetMapping("/sort")
    public String displaysort(Model model, int param1){
        model.addAttribute("courses",classService.getSortedClasses(param1));

        return "classDisplay";
    }

    @GetMapping("/search")
    public String search(Model model,@RequestParam String param1, @RequestParam String param2,
                         @RequestParam(required = false) Optional<Integer> param3, @RequestParam(required = false) Optional<Integer> param4){
        int param3a = 0;
        int param4a = 0;
        if(param3.isPresent()){
            param3a = param3.get();
        }
        if(param4.isPresent()){
            param4a = param4.get();
        }
        model.addAttribute("courses",classService.getSearchClasses(param1, param2, param3a,param4a,"00000000"));

        return "classDisplay";
    }

    @GetMapping("/enroll/{course_ID}")
    public String enroll(Model model,@PathVariable String course_ID){
        //attempts to add course to users list
        if(userService.addCourse("00000000",course_ID)){
            //if successful, updates the course
            classService.addUser("00000000", course_ID);
        }
        return "redirect:/classDisplay";
    }

    @GetMapping("/delclass/{class_ID}")
    public String delclass(@PathVariable String class_ID){
        List<users> students = userService.getStudents(class_ID);
        for(users student : students){
            userService.removeClass(student.getUser_ID(),class_ID);
        }
        classService.deleteClass(class_ID);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admindisplay(Model model){
            model.addAttribute("courses", classService.getClasses());
            return "admin";
    }
    @GetMapping("/asort")
    public String displayasort(Model model, int param1){
        model.addAttribute("courses",classService.getSortedClasses(param1));

        return "admin";
    }

    @GetMapping("/asearch")
    public String asearch(Model model,@RequestParam String param1, @RequestParam String param2,
                         @RequestParam(required = false) Optional<Integer> param3){
        int param3a = 0;
        int param4a = 0;
        if(param3.isPresent()){
            param3a = param3.get();
        }
        model.addAttribute("courses",classService.getSearchClasses(param1, param2, param3a,param4a,"00000000"));

        return "admin";
    }

    //Michael additions
    //
    //
    @GetMapping("/addClass")
    public String addClassForm(Model model){
        model.addAttribute("courselist",classService.getCoursesList());
        return "addClass";
    }

    @PostMapping("/addClass")
    public String addingClass(Model model, @RequestParam String course_ID, @RequestParam String capacity,
                              @RequestParam String professor){ //, @RequestParam String course){   @RequestParam String course_ID,
        try{
            int cap = Integer.parseInt(capacity);
            if(classService.isGoodCap(cap)) {
                Random random = new Random();
                String x = String.valueOf(random.nextInt(900) + 100);

                //generate course ID
                String class_number = course_ID.substring(course_ID.length() - 3);
                String new_class_ID = "2021" + class_number + x;

                //add stuff to class and return to classDisplay
                classService.addClass(new_class_ID, course_ID, cap, professor);
            }

        }catch(Exception e){
        }
        return "redirect:/admin";
    }

    @GetMapping("/editClass/{class_ID}")
    public String editClass(Model model, @PathVariable String class_ID){
        classes cls = classService.getTheClass(class_ID); //For this .getTheClass other .java's say course_ID, but its really class_id
        model.addAttribute("cls", cls);
        return "editClass";
    }
    @PostMapping("/editClass")
    public String editClassSubmit(@RequestParam String capacity, @RequestParam String professor,
                                  @RequestParam String class_ID){
        try{
            int cap = Integer.parseInt(capacity);
            classService.editCap(cap, class_ID);
            classService.editProf(professor,class_ID);
        }catch(Exception e){
        }
        return "redirect:/admin";
    }

}
