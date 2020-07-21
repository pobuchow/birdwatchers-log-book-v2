package com.blb.main.service;

import com.blb.main.dao.ObservationRepository;
import com.blb.main.dto.ObservationTO;
import com.blb.main.service.exception.UserAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private UserService userService;

    public List<ObservationTO> getLastObservationsForAuthUser(Integer size) throws UserAuthenticationException {
        return observationRepository.findByUserId(userService.getAuthorizedUserId()).stream()
                .limit(size)
                .map(ObservationTO::new)
                .collect(Collectors.toList());
    }

}
