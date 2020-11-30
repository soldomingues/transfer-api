package br.com.solangedomingues.transferapi.service.integration.controller;

import br.com.solangedomingues.transferapi.service.integration.SpringIntegrationTest;
import br.com.solangedomingues.transferapi.response.Response;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerSteps extends SpringIntegrationTest {
    private static final Gson GSON = new Gson();


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
        Response resultCustomer = GSON.fromJson(latestResponse.getBody(), Response.class);
        assertThat("customer list not present", resultCustomer.getResult() != null);
    }
}
