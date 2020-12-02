package br.com.solangedomingues.transferapi.service.integration.controller;

import br.com.solangedomingues.transferapi.entity.Transfer;
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

public class TransferStepsTest extends SpringIntegrationTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    protected TestRestTemplate restTemplate;

    @When("the client calls \\/v1\\/transfers with a existing {long} and a existing {long} and a positive {bigdecimal} under thousand")
    public void the_client_calls_v1_transfers_with_a_existing_and_a_existing_and_a_positive_under_thousand(Long originAccount, Long destinationAccount, BigDecimal value) throws IOException {
        Transfer transfer = new Transfer(null, originAccount, destinationAccount, value, null, null);
        executePost("http://localhost:8080/v1/transfers", transfer);
    }

    @Then("the client receives status transfer code of {int}")
    public void the_client_receives_status_transfer_code_of(Integer expectedCode) {
        int receivedStatusCode = latestResponse.getStatusCode().value();
        assertThat("status code is incorrect : ", receivedStatusCode, is(expectedCode));
    }

    @Then("the client receives the registered transfer with the id")
    public void the_client_receives_the_registered_transfer_with_the_id() {
        Transfer resultTransfer = MAPPER.convertValue(latestResponse.getResponseDTO().getResult(), Transfer.class);
        assertThat("customer id must not be null", !Objects.isNull(resultTransfer.getId()));
    }

    @When("the client calls \\/v1\\/transfers with {long} and {long} and a negative {bigdecimal}")
    public void the_client_calls_v1_transfers_with_and_and_a_negative(Long originAccount, Long destinationAccount, BigDecimal value) throws IOException {
        Transfer transfer = new Transfer(null, originAccount, destinationAccount, value, null, null);
        executePost("http://localhost:8080/v1/transfers", transfer);
    }

    @When("the client calls \\/v1\\/transfers with {long} and {long} and {bigdecimal} greater than")
    public void the_client_calls_v1_transfers_with_and_and_greater_than(Long originAccount, Long destinationAccount, BigDecimal value) throws IOException {
        Transfer transfer = new Transfer(null, originAccount, destinationAccount, value, null, null);
        executePost("http://localhost:8080/v1/transfers", transfer);
    }

    @When("the client calls \\/v1\\/transfers with {long} or {long} not existing and {bigdecimal}")
    public void the_client_calls_v1_transfers_with_or_not_existing_and(Long originAccount, Long destinationAccount, BigDecimal value) throws IOException {
        Transfer transfer = new Transfer(null, originAccount, destinationAccount, value, null, null);
        executePost("http://localhost:8080/v1/transfers", transfer);
    }

    @When("the client calls \\/v1\\/transfers with {long} negative balance and {long} and {bigdecimal}")
    public void the_client_calls_v1_transfers_with_negative_balance_and_and(Long originAccount, Long destinationAccount, BigDecimal value) throws IOException {
        Transfer transfer = new Transfer(null, originAccount, destinationAccount, value, null, null);
        executePost("http://localhost:8080/v1/transfers", transfer);
    }

    @When("the client calls \\/v1\\/transfers\\/account with a existing {int}")
    public void the_client_calls_v1_transfers_account_with_a_existing(Integer accountNumber) throws IOException {
        String stringUrl = "http://localhost:8080/v1/transfers/account/" + accountNumber;
        executeGet(stringUrl);
    }

    @Then("the client receives transfers list")
    public void the_client_receives_transfers_list() {
        assertThat("transfers not present", latestResponse.getResponseDTO().getResult() != null);
    }


}
