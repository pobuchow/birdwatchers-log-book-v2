package com.blb.main.service;

import com.blb.main.dao.ObservationRepository;
import com.blb.main.dto.ObservationTO;
import com.blb.main.entity.Observation;
import com.blb.main.entity.User;
import com.blb.main.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ObservationService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ObservationServiceTest {

    @Autowired
    private ObservationService observationService;

    @MockBean
    private ObservationRepository observationRepository;

    @MockBean
    private UserService userService;

    @Mock
    private User user;

    private final static String BLACK_WOODPECKER = "Black woodpecker";
    private final static String EUROPEAN_GREEN_WOODPECKER = "European green woodpecker";
    private final static String MIDDLE_SPOTTED_WOODPECKER = "Middle spotted woodpecker";
    private final static String EURASIAN_THREE_TOED_WOODPECKER = "Eurasian three-toed woodpecker";

    @Test
    @DisplayName("Should get last 5 observations for mocked user")
    void getLastObservationsForAuthUser() throws UserNotFoundException {
        final long userId = 1L;
        Mockito.doReturn(userId).when(userService).getAuthorizedUserId();
        Mockito.doReturn(
                Arrays.asList(
                        new Observation(BLACK_WOODPECKER, LocalDate.of(2020, 4, 17), user),
                        new Observation(EUROPEAN_GREEN_WOODPECKER, LocalDate.of(2020, 4, 18), user),
                        new Observation(MIDDLE_SPOTTED_WOODPECKER, LocalDate.of(2020, 4, 19), user),
                        new Observation(EURASIAN_THREE_TOED_WOODPECKER, LocalDate.of(2020, 4, 20), user),
                        new Observation(EUROPEAN_GREEN_WOODPECKER, LocalDate.of(2020, 3, 20), user),
                        new Observation(BLACK_WOODPECKER, LocalDate.of(2020, 4, 17), user)))
                .when(observationRepository).findByUserIdOrderByDateAsc(userId);

        final List<ObservationTO> result = observationService.getLastObservationsForAuthUser(5);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
        result.forEach(observation -> Assertions.assertTrue(observation.getDate().isAfter(LocalDate.of(2020, 3, 20))));
    }


}