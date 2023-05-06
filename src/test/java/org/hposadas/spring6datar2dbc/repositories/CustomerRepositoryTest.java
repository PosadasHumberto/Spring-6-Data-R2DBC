package org.hposadas.spring6datar2dbc.repositories;

import org.hposadas.spring6datar2dbc.config.DatabaseConfig;
import org.hposadas.spring6datar2dbc.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveNewCustomer() {
        Customer c = Customer.builder().customerName("Test Customer").build();
        customerRepository.save(c).subscribe(customer -> System.out.println(customer.toString()));
        customerRepository.count().subscribe(System.out::println);

    }
}