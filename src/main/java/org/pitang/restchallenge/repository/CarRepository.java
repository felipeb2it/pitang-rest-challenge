package org.pitang.restchallenge.repository;

import org.pitang.restchallenge.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

}
