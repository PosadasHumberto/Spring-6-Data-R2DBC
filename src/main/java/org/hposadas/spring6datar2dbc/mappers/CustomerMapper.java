package org.hposadas.spring6datar2dbc.mappers;

import org.hposadas.spring6datar2dbc.domain.Customer;
import org.hposadas.spring6datar2dbc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper{

    Customer customerDTOtoCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
