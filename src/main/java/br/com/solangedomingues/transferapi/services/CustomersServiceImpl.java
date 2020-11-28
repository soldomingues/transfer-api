package br.com.solangedomingues.transferapi.services;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomerRepository customerRepository;

    public CustomersServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findByNumber(Long number) {
        return customerRepository.findByNumber(number);
    }

    @Override
    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
}
