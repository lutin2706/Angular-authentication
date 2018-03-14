package be.formation.backend.controller;

import be.formation.backend.model.entity.User;
import be.formation.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepo;

    public AdminController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll()
                .stream()
                .peek(u  -> u.setPassword(null))
                .collect(Collectors.toList());
    }
}
