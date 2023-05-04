package org.hposadas.spring6datar2dbc.repositories;

import org.hposadas.spring6datar2dbc.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {

}
