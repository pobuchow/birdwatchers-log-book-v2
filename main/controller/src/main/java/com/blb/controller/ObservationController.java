package com.blb.controller;

import com.blb.dto.ObservationTO;
import com.blb.service.ObservationService;
import com.blb.service.exception.ObservationNotFoundException;
import com.blb.service.exception.OperationNotAllowedException;
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

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteObservationForAuthUser(@PathVariable(name="id") Long id) {
        try {
            if (observastionService.deleteObservationForAuthUser(id)) return HttpStatus.OK;
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User not found", e);
        } catch (ObservationNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Observation not found", e);
        } catch (OperationNotAllowedException e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }
}
