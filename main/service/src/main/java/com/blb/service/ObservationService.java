package com.blb.service;

import com.blb.dto.ObservationTO;
import com.blb.entity.Observation;
import com.blb.entity.User;
import com.blb.repository.ObservationRepository;
import com.blb.service.exception.ObservationNotFoundException;
import com.blb.service.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(ObservationService.class);

    public List<ObservationTO> getLastObservationsForAuthUser(Integer size) throws UserNotFoundException {
        return observationRepository.findByUserIdOrderByDateAsc(userService.getAuthenticatedUserId()).stream()
                .sorted(Comparator.comparing(Observation::getDate).reversed())
                .limit(size)
                .map(o -> new ObservationTO(o.getId(), o.getSpeciesName(), o.getDate(), o.getUser().getUserName()))
                .collect(Collectors.toList());
    }

    public boolean deleteObservationForAuthUser(Long observationId) throws ObservationNotFoundException, UserNotFoundException {
        final Observation observation = observationRepository.findById(observationId)
                .orElseThrow(() ->
                        new ObservationNotFoundException(observationId));
        final User user = observation.getUser();
        if(Objects.isNull(user)){
            logger.error("Observation: {} has no user", observationId);
            return false;
        }
        if(userService.getAuthenticatedUserId().equals(user.getId())){
            observationRepository.delete(observation);
            return true;
        }
        return false;
    }
}
