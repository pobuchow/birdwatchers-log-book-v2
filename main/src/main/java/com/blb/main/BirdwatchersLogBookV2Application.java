package com.blb.main;

import com.blb.main.config.MockData;
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
                final User diegoUser = new User(MockData.USERS.DIEGO.username, MockData.USERS.DIEGO.password, MockData.USERS.DIEGO.email);
                userRepository.saveAll(Arrays.asList(
                        diegoUser,
                        new User(MockData.USERS.LESTER.username, MockData.USERS.LESTER.password,MockData.USERS.LESTER.email )
                ));
                observationRepository.saveAll(Arrays.asList(
                        new Observation(MockData.SPECIES_NAME.BLACK_WOODPECKER.name, LocalDate.of(2020, 4, 17), diegoUser),
                        new Observation(MockData.SPECIES_NAME.EUROPEAN_GREEN_WOODPECKER.name, LocalDate.of(2020, 4, 18), diegoUser),
                        new Observation(MockData.SPECIES_NAME.MIDDLE_SPOTTED_WOODPECKER.name, LocalDate.of(2020, 4, 19), diegoUser),
                        new Observation(MockData.SPECIES_NAME.EURASIAN_THREE_TOED_WOODPECKER.name, LocalDate.of(2020, 4, 20), diegoUser),
                        new Observation(MockData.SPECIES_NAME.BLACK_WOODPECKER.name, LocalDate.of(2020, 4, 17), diegoUser),
                        new Observation(MockData.SPECIES_NAME.BLACK_WOODPECKER.name, LocalDate.of(2019, 11, 20), diegoUser),
                        new Observation(MockData.SPECIES_NAME.EUROPEAN_GREEN_WOODPECKER.name, LocalDate.of(2019, 11, 21), diegoUser),
                        new Observation(MockData.SPECIES_NAME.MIDDLE_SPOTTED_WOODPECKER.name, LocalDate.of(2019, 11, 23), diegoUser),
                        new Observation(MockData.SPECIES_NAME.EURASIAN_THREE_TOED_WOODPECKER.name, LocalDate.of(2019, 11, 30), diegoUser),
                        new Observation(MockData.SPECIES_NAME.MIDDLE_SPOTTED_WOODPECKER.name, LocalDate.of(2019, 11, 30), diegoUser),
                        new Observation(MockData.SPECIES_NAME.EUROPEAN_GREEN_WOODPECKER.name, LocalDate.of(2019, 11, 30), diegoUser)
                ));
            } catch (UserCreationException e) {
                e.printStackTrace();
            }
        };
    }

}
