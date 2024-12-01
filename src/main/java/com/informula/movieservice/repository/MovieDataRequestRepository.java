package com.informula.movieservice.repository;

import com.informula.movieservice.model.entity.MovieDataRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDataRequestRepository extends CrudRepository<MovieDataRequestEntity, Long> {

}
