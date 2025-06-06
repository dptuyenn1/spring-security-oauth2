package com.dev;

import com.dev.enums.Authority;
import com.dev.models.Role;
import com.dev.models.User;
import com.dev.services.UserService;
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
            Role roleUser = Role.builder().authority(Authority.USER).build();
            Role roleAdmin = Role.builder().authority(Authority.ADMIN).build();

            User admin = User.builder().firstName("Tuyen").lastName("Dang").username("admin").password("123").build();

            admin.getRoles().add(roleUser);
            admin.getRoles().add(roleAdmin);

            userService.create(admin);
        };
    }

}
