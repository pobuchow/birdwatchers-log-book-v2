package com.blb.main.controller;

import com.blb.main.dto.ObservationTO;
import com.blb.main.service.ObservationService;
import com.blb.main.service.exception.UserAuthenticationException;
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
    public List<ObservationTO> getLastObservationsForAuthUser(@PathVariable(name="size") Integer size) throws UserAuthenticationException {
        return observastionService.getLastObservationsForAuthUser(size);
    }
}
