package br.com.solangedomingues.transferapi.controler;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transfer;
import br.com.solangedomingues.transferapi.service.AccountService;
import br.com.solangedomingues.transferapi.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {

        Optional<Customer> savedCustomer = accountService.saveCustomer(customer);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(savedCustomer, situation);

        return new ResponseEntity<Response>(response, HttpStatus.CREATED);

    }

    @GetMapping("/customers")
    public ResponseEntity<Response> retrieveAllCustomers() {

        List<Customer> listCostumers = accountService.findAllCostumers();

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(listCostumers, situation);

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/account/{accountNumber}")
    public ResponseEntity<Response> retrieveCustomerByAccountNumber(@PathVariable Long accountNumber, @RequestHeader HttpHeaders headers) {
        Optional<Customer> customer = accountService.findByAccountNumber(accountNumber);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(customer, situation);

        return new ResponseEntity<Response>(response, HttpStatus.OK);

    }

    @PostMapping("/transfers")
    public ResponseEntity<Response> createTransfer(@RequestBody Transfer transfer) {

        Optional<Transfer> savedTransaction = accountService.makeTransfer(transfer);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(savedTransaction, situation);

        return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }

    @GetMapping("/transfers/account/{accountNumber}")
    public ResponseEntity<Response> retrieveTransfersByAccountNumber(@PathVariable Long accountNumber) {
        List<Transfer> transactionsForAccount = accountService.findAllTransfersByAccount(accountNumber);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(transactionsForAccount, situation);

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
