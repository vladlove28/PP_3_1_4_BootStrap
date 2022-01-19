package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
    @PostConstruct
    public void initUsers() {
        Role adminRole = new Role("ROLE_ADMIN");

        roleService.addRole(adminRole);

        Role userRole = new Role("ROLE_USER");

        roleService.addRole(userRole);

        User user = new User();
        user.setName("user");
        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setRoles(Collections.singleton(userRole));
        userService.saveUser(user);

        User user1 = new User();
        user1.setName("admin");
        user1.setEmail("admin@mail.ru");
        user1.setPassword("admin");
        user1.setRoles(Collections.singleton(adminRole));
        userService.saveUser(user1);
    }
}
