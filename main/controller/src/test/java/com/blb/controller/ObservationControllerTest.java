package com.blb.controller;

import com.blb.entity.Observation;
import com.blb.entity.User;
import com.blb.service.ObservationService;
import com.blb.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = ObservationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ObservationControllerTest {

    private static final String BASIC_OBSERVATION_PATH = "/observations";
    private static final String GET_LAST_PATH = "/getLast/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ObservationService observationService;

    private final static String BLACK_WOODPECKER = "Black woodpecker";
    private final static String EUROPEAN_GREEN_WOODPECKER = "European green woodpecker";
    private final static String MIDDLE_SPOTTED_WOODPECKER = "Middle spotted woodpecker";
    private final static String EURASIAN_THREE_TOED_WOODPECKER = "Eurasian three-toed woodpecker";

    @Test
    @DisplayName("Should get mocked observations")
    void getLastObservationsForAuthUser() throws Exception {

        User mockedUser = new User("USER_A", "C0rrect!", "user@qwe.abc");

        Mockito.doReturn(
                Arrays.asList(
                        new Observation(1L, BLACK_WOODPECKER, LocalDate.of(2020, 4, 17),mockedUser),
                        new Observation(2L, EUROPEAN_GREEN_WOODPECKER, LocalDate.of(2020, 4, 18),mockedUser),
                        new Observation(3L, MIDDLE_SPOTTED_WOODPECKER, LocalDate.of(2020, 4, 19), mockedUser),
                        new Observation(4L, EURASIAN_THREE_TOED_WOODPECKER, LocalDate.of(2020, 4, 20), mockedUser),
                        new Observation(5L, MIDDLE_SPOTTED_WOODPECKER, LocalDate.of(2020, 4, 17), mockedUser)))
                .when(observationService).getLastObservationsForAuthUser(5);


        mockMvc.perform(MockMvcRequestBuilders
                .get(BASIC_OBSERVATION_PATH + GET_LAST_PATH + 5))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].speciesName").exists())
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[0].user").exists());
    }
}