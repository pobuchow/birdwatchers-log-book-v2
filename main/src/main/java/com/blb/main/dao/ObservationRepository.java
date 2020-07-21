package com.blb.main.dao;

import com.blb.main.entity.Observation;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ObservationRepository extends CrudRepository<Observation, Long> {
    Collection<Observation> findByUserId(long id);
}
