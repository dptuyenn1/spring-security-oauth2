package com.dev.demo;

import com.dev.demo.models.Role;
import com.dev.demo.models.User;
import com.dev.demo.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {
            Role roleUser = Role.builder().name("ROLE_USER").build();
            Role roleAdmin = Role.builder().name("ROLE_ADMIN").build();
            Role roleSuperAdmin = Role.builder().name("ROLE_SUPER_ADMIN").build();

            User admin = User.builder().username("admin").password("123").build();

            admin.getRoles().add(roleUser);
            admin.getRoles().add(roleAdmin);
            admin.getRoles().add(roleSuperAdmin);

            userService.create(admin);
        };
    }

}
