package br.com.solangedomingues.transferapi.controler;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transaction;
import br.com.solangedomingues.transferapi.service.AccountService;
import br.com.solangedomingues.transferapi.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/customers")
    public ResponseEntity<ResponseCustomer> createCustomer(@RequestBody Customer customer) {

        Optional<Customer> savedCustomer = accountService.saveCustomer(customer);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        ResponseCustomer response = new ResponseCustomer(savedCustomer, situation);

        return new ResponseEntity<ResponseCustomer>(response, HttpStatus.OK);

    }

    @GetMapping("/customers")
    public ResponseEntity<ResponseCustomers> retrieveAllCustomers() {

        List<Customer> listCostumers = accountService.findAllCostumers();

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        ResponseCustomers response = new ResponseCustomers(listCostumers, situation);

        return new ResponseEntity<ResponseCustomers>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/account/{accountNumber}")
    public ResponseEntity<ResponseCustomer> retrieveCustomerByAccountNumber(@PathVariable Long accountNumber, @RequestHeader HttpHeaders headers) {
        Optional<Customer> customer = accountService.findByAccountNumber(accountNumber);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        ResponseCustomer response = new ResponseCustomer(customer, situation);

        return new ResponseEntity<ResponseCustomer>(response, HttpStatus.OK);

    }

    @PostMapping("/transactions")
    public ResponseEntity<ResponseTransaction> createTransactions(@RequestBody Transaction transaction) {

        Optional<Transaction> savedTransaction = accountService.saveTransaction(transaction);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        ResponseTransaction response = new ResponseTransaction(savedTransaction, situation);

        return new ResponseEntity<ResponseTransaction>(response, HttpStatus.OK);
    }

    @GetMapping("/transactions/account/{accountNumber}")
    public ResponseEntity<ResponseTransactions> retrieveTransactionsByAccountNumber(@PathVariable Long accountNumber) {
        List<Transaction> transactionsForAccount = accountService.findAllTransactionsByAccount(accountNumber);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        ResponseTransactions response = new ResponseTransactions(transactionsForAccount, situation);

        return new ResponseEntity<ResponseTransactions>(response, HttpStatus.OK);
    }
}
