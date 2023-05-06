package org.hposadas.spring6datar2dbc.services;

import lombok.RequiredArgsConstructor;
import org.hposadas.spring6datar2dbc.mappers.CustomerMapper;
import org.hposadas.spring6datar2dbc.model.CustomerDTO;
import org.hposadas.spring6datar2dbc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDTOtoCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setCustomerName(customerDTO.getCustomerName());
            return customer;
        }).flatMap(customerToSave -> customerRepository.save(customerToSave)
                .map(customerMapper::customerToCustomerDTO));
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).map(foundCustomer -> {
            if (StringUtils.hasText(customerDTO.getCustomerName())){
                foundCustomer.setCustomerName(customerDTO.getCustomerName());
            }
            return foundCustomer;
        }).flatMap(customerModif -> customerRepository.save(customerModif))
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<Void> deleteCustomerById(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
