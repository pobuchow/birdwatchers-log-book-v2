package com.blb.controller;

import com.blb.dto.ObservationTO;
import com.blb.service.ObservationService;
import com.blb.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/observations")
public class ObservationController {

    @Autowired
    private ObservationService observastionService;

    @ResponseBody
    @GetMapping(path = "/getLast/{size}")
    public List<ObservationTO> getLastObservationsForAuthUser(@PathVariable(name="size") Integer size) {
        try {
            return observastionService.getLastObservationsForAuthUser(size);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User not found", e);
        }
    }
}
