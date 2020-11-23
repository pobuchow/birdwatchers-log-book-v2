package com.blb.repository;

import com.blb.entity.Observation;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ObservationRepository extends CrudRepository<Observation, Long> {
    Collection<Observation> findByUserIdOrderByDateAsc(long id);
}
