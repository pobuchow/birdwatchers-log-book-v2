package com.blb.main;

import com.blb.main.dao.UserRepository;
import com.blb.main.entity.User;
import com.blb.main.entity.exception.UserCreationException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BirdwatchersLogBookV2Application {

    public static void main(String[] args) {
        SpringApplication.run(BirdwatchersLogBookV2Application.class, args);
    }

    @Bean
    ApplicationRunner init(UserRepository userRepository){
        return args -> {
            try {
                userRepository.saveAll(Arrays.asList(
                        new User("Diego", "diego@old-camp.xyz"),
                        new User("Lester", "lester@swamp-camp.abc")
                ));
            } catch (UserCreationException e) {
                e.printStackTrace();
            }
        };
    }

}
