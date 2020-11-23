package com.blb.service;

import com.blb.repository.ObservationRepository;
import com.blb.dto.ObservationTO;
import com.blb.entity.Observation;
import com.blb.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private UserService userService;

    public List<ObservationTO> getLastObservationsForAuthUser(Integer size) throws UserNotFoundException {
        return observationRepository.findByUserIdOrderByDateAsc(userService.getAuthenticatedUserId()).stream()
                .sorted(Comparator.comparing(Observation::getDate).reversed())
                .limit(size)
                .map(o -> new ObservationTO(o.getId(), o.getSpeciesName(), o.getDate(), o.getUser().getUserName()))
                .collect(Collectors.toList());
    }

}
