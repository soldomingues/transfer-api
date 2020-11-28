package br.com.solangedomingues.transferapi.services;

import br.com.solangedomingues.transferapi.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomersService {
    Optional<Customer> findByNumber(Long number);
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
}
