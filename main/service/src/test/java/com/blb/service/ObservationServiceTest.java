package com.blb.service;

import com.blb.repository.ObservationRepository;
import com.blb.dto.ObservationTO;
import com.blb.entity.Observation;
import com.blb.entity.User;
import com.blb.service.exception.ObservationNotFoundException;
import com.blb.service.exception.OperationNotAllowedException;
import com.blb.service.exception.UserNotFoundException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Mockito.doReturn(userId).when(userService).getAuthenticatedUserId();
        Mockito.doReturn(
                Arrays.asList(
                        new Observation(1L, BLACK_WOODPECKER, LocalDate.of(2020, 4, 17), user),
                        new Observation(2L, EUROPEAN_GREEN_WOODPECKER, LocalDate.of(2020, 4, 18), user),
                        new Observation(3L, MIDDLE_SPOTTED_WOODPECKER, LocalDate.of(2020, 4, 19), user),
                        new Observation(4L, EURASIAN_THREE_TOED_WOODPECKER, LocalDate.of(2020, 4, 20), user),
                        new Observation(5L, EUROPEAN_GREEN_WOODPECKER, LocalDate.of(2020, 3, 20), user),
                        new Observation(6L, BLACK_WOODPECKER, LocalDate.of(2020, 4, 17), user)))
                .when(observationRepository).findByUserIdOrderByDateAsc(userId);

        final List<ObservationTO> result = observationService.getLastObservationsForAuthUser(5);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
        result.forEach(observation -> Assertions.assertTrue(observation.getDate().isAfter(LocalDate.of(2020, 3, 20))));
    }

    @Test
    @DisplayName("Should get last observation for mocked user with all attributes")
    void getLastObservationForAuthUser() throws UserNotFoundException {
        final long userId = 1L;
        Mockito.doReturn(userId).when(userService).getAuthenticatedUserId();
        final LocalDate observationDate = LocalDate.of(2020, 4, 17);
        Mockito.doReturn(
                Collections.singletonList(
                        new Observation(1L, BLACK_WOODPECKER, observationDate, user)))
                .when(observationRepository).findByUserIdOrderByDateAsc(userId);

        final List<ObservationTO> result = observationService.getLastObservationsForAuthUser(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        ObservationTO resultObservation = result.get(0);
        Assertions.assertEquals(BLACK_WOODPECKER, resultObservation.getSpeciesName());
        Assertions.assertEquals(observationDate, resultObservation.getDate());
        Assertions.assertEquals(user.getUserName(), resultObservation.getUsername());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user not found")
    void getLastObservationsForNotExistingUser() throws UserNotFoundException {
        Mockito.doThrow(new UserNotFoundException("user")).when(userService).getAuthenticatedUserId();
        Assertions.assertThrows(UserNotFoundException.class,
                () -> observationService.getLastObservationsForAuthUser(1));
    }

    @Test
    @DisplayName("Should throw ObservationNotFoundException when observation not found")
    void deleteNotFoundObservation(){
        Long observationId = 4L;
        Mockito.doReturn(Optional.empty()).when(observationRepository).findById(observationId);
        Assertions.assertThrows(
                ObservationNotFoundException.class,
                () -> observationService.deleteObservationForAuthUser(observationId),
                "Observation with id: " + observationId + " not found");
    }

    @Test
    @DisplayName("Should return false when observation has no user")
    void deleteObservationUnprocessable() throws UserNotFoundException, ObservationNotFoundException, OperationNotAllowedException {
        Long observationId = 4L;
        Mockito.doReturn(Optional.of(new Observation())).when(observationRepository).findById(observationId);
        Assertions.assertEquals(false, observationService.deleteObservationForAuthUser(observationId));
    }

    @Test
    @DisplayName("Should throw OperationNotAllowedException when observation does not belong to auth user")
    void deleteObservationThatNotBelongsToUser() throws UserNotFoundException, ObservationNotFoundException, OperationNotAllowedException {
        Long observationId = 4L;
        Long userId = 5L;
        Mockito.doReturn(userId).when(user).getId();
        Mockito.doReturn(Optional.of(new Observation(observationId, "speciesName", null, user))).when(observationRepository).findById(observationId);
        Mockito.doReturn(userId+1L).when(userService).getAuthenticatedUserId();
        Assertions.assertThrows(
                OperationNotAllowedException.class,
                () -> observationService.deleteObservationForAuthUser(observationId),
                "Requested operation is not allowed for current user");
    }

    @Test
    @DisplayName("Should return true when observation deleted")
    void deleteObservation() throws UserNotFoundException, ObservationNotFoundException, OperationNotAllowedException {
        Long observationId = 4L;
        Long userId = 5L;
        Mockito.doReturn(userId).when(user).getId();
        Mockito.doReturn(Optional.of(new Observation(observationId, "speciesName", null, user))).when(observationRepository).findById(observationId);
        Mockito.doReturn(userId).when(userService).getAuthenticatedUserId();
        Assertions.assertEquals(true, observationService.deleteObservationForAuthUser(observationId));
    }

}