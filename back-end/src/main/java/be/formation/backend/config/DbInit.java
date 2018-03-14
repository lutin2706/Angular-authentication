package be.formation.backend.config;

import be.formation.backend.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements InitializingBean {

    private final UserService userService;

    public DbInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        userService.register("user", "user");
        userService.register("toto", "toto");
    }
}
