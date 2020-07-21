package com.blb.main;

import com.blb.main.dao.ObservationRepository;
import com.blb.main.dao.UserRepository;
import com.blb.main.entity.Observation;
import com.blb.main.entity.User;
import com.blb.main.service.exception.UserCreationException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class BirdwatchersLogBookV2Application {

    public static void main(String[] args) {
        SpringApplication.run(BirdwatchersLogBookV2Application.class, args);
    }

    @Bean
    ApplicationRunner init(UserRepository userRepository, ObservationRepository observationRepository){
        return args -> {
            try {
                final User diegoUser = new User("Diego", "Dieg0!pass", "diego@old-camp.xyz");
                userRepository.saveAll(Arrays.asList(
                        diegoUser,
                        new User("Lester", "Lester0!pass", "lester@swamp-camp.abc")
                ));
                observationRepository.saveAll(Arrays.asList(
                        new Observation("Black woodpecker", LocalDate.of(2020, 4, 17), diegoUser),
                        new Observation("European green woodpecker", LocalDate.of(2020, 4, 18), diegoUser),
                        new Observation("Middle spotted woodpecker", LocalDate.of(2020, 4, 19), diegoUser),
                        new Observation("Eurasian three-toed woodpecker", LocalDate.of(2020, 4, 20), diegoUser),
                        new Observation("Black woodpecker", LocalDate.of(2020, 4, 17), diegoUser),
                        new Observation("Black woodpecker", LocalDate.of(2019, 11, 20), diegoUser),
                        new Observation("European green woodpecker", LocalDate.of(2019, 11, 21), diegoUser),
                        new Observation("Middle spotted woodpecker", LocalDate.of(2019, 11, 23), diegoUser),
                        new Observation("Eurasian three-toed woodpecker", LocalDate.of(2019, 11, 30), diegoUser),
                        new Observation("Middle spotted woodpecker", LocalDate.of(2019, 11, 30), diegoUser),
                        new Observation("European green woodpecker", LocalDate.of(2019, 11, 30), diegoUser)
                ));
            } catch (UserCreationException e) {
                e.printStackTrace();
            }
        };
    }

}
