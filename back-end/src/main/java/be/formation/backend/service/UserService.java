package be.formation.backend.service;

import be.formation.backend.model.entity.User;

public interface UserService {

    User register(String username, String password, boolean isAdmin);
}
