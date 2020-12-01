package br.com.solangedomingues.transferapi.controler;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transfer;
import br.com.solangedomingues.transferapi.service.AccountService;
import br.com.solangedomingues.transferapi.response.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Controller" , description = "Endpoints for creating new accounts and transfers between them")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered Customer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Data", content = @Content)})
    @PostMapping("/customers")
    public ResponseEntity<Response> createCustomer(@Parameter(description = "Customer Object to be Created") @RequestBody Customer customer) {

        Optional<Customer> savedCustomer = accountService.saveCustomer(customer);

        Situation situation = new Situation(HttpStatus.CREATED.value(), "Success", new Date(), null, null);

        Response response = new Response(savedCustomer, situation);

        return new ResponseEntity<Response>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "Retrieve All Registered Customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Customers",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Customer.class))) })})
    @GetMapping("/customers")
    public ResponseEntity<Response> retrieveAllRegisteredCustomers() {

        List<Customer> listCostumers = accountService.findAllCostumers();

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(listCostumers, situation);

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Customer By Account Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Customer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid accountNumber Supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer Not Found", content = @Content) })
    @GetMapping("/customers/account/{accountNumber}")
    public ResponseEntity<Response> retrieveCustomerByAccountNumber(@Parameter(description = "Account Number to be Searched")
                                                                        @PathVariable Long accountNumber, @RequestHeader HttpHeaders headers) {
        Optional<Customer> customer = accountService.findByAccountNumber(accountNumber);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(customer, situation);

        return new ResponseEntity<Response>(response, HttpStatus.OK);

    }

    @Operation(summary = "Create Transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered Transfer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer Not Found", content = @Content) })
    @PostMapping("/transfers")
    public ResponseEntity<Response> createTransfer(@Parameter(description = "Transfer Object to be Created") @RequestBody Transfer transfer) {

        Optional<Transfer> savedTransaction = accountService.makeTransfer(transfer);

        Situation situation = new Situation(HttpStatus.CREATED.value(), "Success", new Date(), null, null);

        Response response = new Response(savedTransaction, situation);

        return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve Transfers By Account Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Transfers",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transfer.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid accountNumber Supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @GetMapping("/transfers/account/{accountNumber}")
    public ResponseEntity<Response> retrieveTransfersByAccountNumber(@Parameter(description = "Account Number to be searched for transfers") @PathVariable Long accountNumber) {
        List<Transfer> transactionsForAccount = accountService.findAllTransfersByAccount(accountNumber);

        Situation situation = new Situation(HttpStatus.OK.value(), "Success", new Date(), null, null);

        Response response = new Response(transactionsForAccount, situation);

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
