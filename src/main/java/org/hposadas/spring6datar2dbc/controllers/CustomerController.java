package org.hposadas.spring6datar2dbc.controllers;

import lombok.RequiredArgsConstructor;
import org.hposadas.spring6datar2dbc.model.CustomerDTO;
import org.hposadas.spring6datar2dbc.repositories.CustomerRepository;
import org.hposadas.spring6datar2dbc.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/v2/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO){
        return customerService.saveNewCustomer(customerDTO)
                .map(savedCustomer -> ResponseEntity.created(UriComponentsBuilder
                            .fromHttpUrl("http://localhost:8080" + CUSTOMER_PATH + "/"
                            +savedCustomer.getId()).build().toUri()).build());
    }

    @PutMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> updateCustomerById(
            @PathVariable("customerId") Integer customerId,
            @Validated @RequestBody CustomerDTO customerDTO
    ) {
        return customerService.updateCustomer(customerId, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(customer -> ResponseEntity.noContent().build());
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingCustomer(
            @PathVariable("customerId") Integer customerId,
            @Validated @RequestBody CustomerDTO customerDTO
    ) {
        return customerService.patchCustomer(customerId, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(customer -> ResponseEntity.ok().build());
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer customerId) {
        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(customerDTO -> customerService.deleteCustomerById(customerDTO.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
