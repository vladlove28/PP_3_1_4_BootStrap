package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;


@Controller
@RequestMapping("/admin")
//@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ResponseBody
    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }



//    в контроллере предлагаю над классом:
//    @RestController
//    @CrossOrigin(origins = "http://localhost:8080")
//    @RequestMapping(path = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
//
//    над методом убрать респонсбоди и сделать так:
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<User> delete(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }




//    в контроллере предлагаю над классом:
//    @RestController
//    @CrossOrigin(origins = "http://localhost:8080")
//    @RequestMapping(path = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
//
//    над методом убрать респонсбоди и сделать так:
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<User> showAllUsers () {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//6:33 PM
//    @RestController уже включает @Controller и @ResponseBody,
//    также контроллер должен знать, в каком формате возвращать ответ - json












    @GetMapping
    public String getAllUsers(@AuthenticationPrincipal User user, Role role, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("new_user", new User());
        model.addAttribute("roles", roleService.getAllRolles());
        model.addAttribute("role", role);
        return "admin/mainPage";
    }

    // Create
    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("authorities") List<String> values
    ) {
        Set<Role> roleSet = userService.getSetOfRoles(values);
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit")
    public String updateUser(@ModelAttribute("user1") User user,
                             @RequestParam("authorities") List<String> values,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        model.addAttribute("roles", roleService.getAllRolles());
        Set<Role> rolesSet = userService.getSetOfRoles(values);
        user.setRoles(rolesSet);
        userService.update(user.getId(), user);
        return "redirect:/admin";
    }

}
