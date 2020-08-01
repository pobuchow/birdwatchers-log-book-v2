package com.blb.main.controller;

import com.blb.main.config.MockData;
import com.blb.main.dao.ObservationRepository;
import com.blb.main.dao.UserRepository;
import com.blb.main.entity.Observation;
import com.blb.main.entity.User;
import com.blb.main.service.ObservationService;
import com.blb.main.service.UserAuthenticationProvider;
import com.blb.main.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ObservationController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
class ObservationControllerTest {

    private static final String BASIC_OBSERVATION_PATH = "/observations";
    private static final String GET_LAST_PATH = "/getLast/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ObservationRepository observationRepository;

    @MockBean
    private ObservationService observationService;

    @MockBean
    private UserAuthenticationProvider userAuthenticationProvider;

    @Test
    @DisplayName("Should get mocked observations")
    void getLastObservationsForAuthUser() throws Exception {

        User mockedUser = new User("USER_A", "C0rrect!", "user@qwe.abc");

        Mockito.doReturn(
                Arrays.asList(
                        new Observation(MockData.SPECIES_NAME.BLACK_WOODPECKER.name, LocalDate.of(2020, 4, 17),mockedUser),
                        new Observation(MockData.SPECIES_NAME.EUROPEAN_GREEN_WOODPECKER.name, LocalDate.of(2020, 4, 18),mockedUser),
                        new Observation(MockData.SPECIES_NAME.MIDDLE_SPOTTED_WOODPECKER.name, LocalDate.of(2020, 4, 19), mockedUser),
                        new Observation(MockData.SPECIES_NAME.EURASIAN_THREE_TOED_WOODPECKER.name, LocalDate.of(2020, 4, 20), mockedUser),
                        new Observation(MockData.SPECIES_NAME.BLACK_WOODPECKER.name, LocalDate.of(2020, 4, 17), mockedUser)))
                .when(observationService).getLastObservationsForAuthUser(5);


        mockMvc.perform(MockMvcRequestBuilders
                .get(BASIC_OBSERVATION_PATH + GET_LAST_PATH + 5))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].speciesName").exists())
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[0].user").exists())
                .andDo(MockMvcResultHandlers.print());
    }
}