package br.com.solangedomingues.transferapi.controler;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transaction;
import br.com.solangedomingues.transferapi.exception.CustomerNotFoundException;
import br.com.solangedomingues.transferapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = accountService.saveCustomer(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCustomer.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/customers")
    public List<Customer> retrieveAllCustomers() {
        return accountService.findAllCostumers();
    }

    @GetMapping("/customers/{id}")
    public Customer retrieveCustomer(@PathVariable Long id) {
        Optional<Customer> customer = accountService.findByIdCostumer(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException("id - " + id);

        return customer.get();
    }

    @GetMapping("/customers/account/{accountNumber}")
    public Customer retrieveCustomerByAccountNumber(@PathVariable Long accountNumber) {
        Optional<Customer> customer = accountService.findByAccountNumber(accountNumber);

        if (customer.isEmpty())
            throw new CustomerNotFoundException("account number - " + accountNumber);

        return customer.get();
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransactions(@RequestBody Transaction transaction) {

    Transaction savedTransaction = accountService.saveTransaction(transaction);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTransaction.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/transactions")
    public List<Transaction> retrieveAllTransactions() {
        return accountService.findAllTransactions();
    }

    @GetMapping("/transactions/{id}")
    public Transaction retrieveTransactions(@PathVariable Long id) {
        Optional<Transaction> transaction = accountService.findByIdTransaction(id);

        if (transaction.isEmpty())
            throw new CustomerNotFoundException("id - " + id);

        return transaction.get();
    }

    @GetMapping("/transactions/account/{accountNumber}")
    public List<Transaction> retrieveTransactionsByAccountNumber(@PathVariable Long accountNumber) {
        List<Transaction> transactionsForAccount = accountService.findAllTransactionsByAccount(accountNumber);

        return transactionsForAccount;
    }
}
