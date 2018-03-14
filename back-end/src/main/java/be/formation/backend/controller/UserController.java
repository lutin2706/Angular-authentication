package be.formation.backend.controller;

import be.formation.backend.model.dto.UserFormDTO;
import be.formation.backend.model.entity.User;
import be.formation.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    public UserController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserFormDTO userFormDTO) {
        User created = userService.register(userFormDTO.username, userFormDTO.password);
        return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
    }

    @GetMapping("/user/whoami")
    public ResponseEntity whoAmI(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
