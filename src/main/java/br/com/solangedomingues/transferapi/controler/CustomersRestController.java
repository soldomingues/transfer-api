package br.com.solangedomingues.transferapi.controler;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.exception.CustomerNotFoundException;
import br.com.solangedomingues.transferapi.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomersRestController {

    @Autowired
    private final CustomersService customersService;

    public CustomersRestController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customersService.save(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCustomer.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/customers")
    public List<Customer> retrieveAllCustomer() {
        return customersService.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer retrieveCustomers(@PathVariable long id) {
        Optional<Customer> customer = customersService.findById(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException("id-" + id);

        return customer.get();
    }

    @GetMapping("/customer/number/{number}")
    public Customer retrieveCustomerForNumber(@PathVariable long number) {
        Optional<Customer> customer = customersService.findByNumber(number);

        if (customer.isEmpty())
            throw new CustomerNotFoundException("number-" + number);

        return customer.get();
    }

}
