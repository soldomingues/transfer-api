package br.com.solangedomingues.transferapi.service.integration;

import br.com.solangedomingues.transferapi.TransferApiApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import java.io.IOException;

@CucumberContextConfiguration
@SpringBootTest(classes = TransferApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {


    @Autowired
    protected TestRestTemplate restTemplate;

    public RestResponse latestResponse;


    public void executeGet(String url) throws IOException {
        latestResponse = restTemplate.execute(url, HttpMethod.GET, null, response -> new RestResponse(response, response.getStatusCode()));
    }

}
