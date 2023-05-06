package org.hposadas.spring6datar2dbc.repositories;

import org.hposadas.spring6datar2dbc.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
}
