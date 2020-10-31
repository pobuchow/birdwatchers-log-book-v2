package com.blb.controller;

import com.blb.dto.ObservationTO;
import com.blb.service.ObservationService;
import com.blb.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/observations")
public class ObservationController {

    @Autowired
    private ObservationService observastionService;

    @ResponseBody
    @GetMapping(path = "/getLast/{size}")
    public List<ObservationTO> getLastObservationsForAuthUser(@PathVariable(name="size") Integer size) throws UserNotFoundException {
        return observastionService.getLastObservationsForAuthUser(size);
    }
}
