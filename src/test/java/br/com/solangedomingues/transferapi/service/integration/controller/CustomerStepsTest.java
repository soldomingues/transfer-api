package br.com.solangedomingues.transferapi.service.integration.controller;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.service.integration.SpringIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerStepsTest extends SpringIntegrationTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    protected TestRestTemplate restTemplate;

    @When("the client calls \\/v1\\/customers")
    public void the_client_calls_v1_customers() throws IOException {
        executeGet("http://localhost:8080/v1/customers");
    }

    @Then("the client receives status code of {int}")
    public void the_client_receives_status_code_of(Integer expectedCode) {
        int receivedStatusCode = latestResponse.getStatusCode().value();
        assertThat("status code is incorrect : ", receivedStatusCode, is(expectedCode));
    }
    @Then("the client receives a list with all customers")
    public void the_client_receives_a_list_with_all_customers() {
        assertThat("customer list not present", latestResponse.getResponse().getResult() != null);
    }

    @When("the client calls \\/v1\\/customers with {long} and {string} and {bigdecimal}")
    public void the_client_calls_v1_customers_with_and_and(Long accountNumber, String name, BigDecimal balance) throws IOException {
        Customer customer = new Customer(null, accountNumber, name, balance);
        executePost("http://localhost:8080/v1/customers", customer);
    }

    @Then("the client receives the registered customer with the id")
    public void the_client_receives_the_registered_customer_with_the_id() {
        Customer resultCustomer = MAPPER.convertValue(latestResponse.getResponse().getResult(), Customer.class);
        assertThat("customer id must not be null", !Objects.isNull(resultCustomer.getId()));
    }

    @When("the client calls \\/v1\\/customers with {long} and {string} and a negative {bigdecimal}")
    public void the_client_calls_v1_customers_with_and_and_a_negative(Long accountNumber, String name, BigDecimal balance) throws IOException {
        Customer customer = new Customer(null, accountNumber, name, balance);
        executePost("http://localhost:8080/v1/customers", customer);
    }

    @When("the client calls \\/v1\\/customers with {long} already exists and {string} and a {bigdecimal}")
    public void the_client_calls_v1_customers_with_already_exists_and_and_a(Long accountNumber, String name, BigDecimal balance) throws IOException {
        Customer customer = new Customer(null, accountNumber, name, balance);
        executePost("http://localhost:8080/v1/customers", customer);
    }

    @When("the client calls \\/v1\\/customers\\/account with {long}")
    public void the_client_calls_v1_customers_account_with(Long accountNumber) throws IOException {
        String stringUrl = "http://localhost:8080/v1/customers/account/" + accountNumber;
        executeGet(stringUrl);
    }

    @Then("the client receives body customer")
    public void the_client_receives_body_customer() {
        assertThat("customer not present", latestResponse.getResponse().getResult() != null);
    }


}
