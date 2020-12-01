package br.com.solangedomingues.transferapi.service.integration;

import br.com.solangedomingues.transferapi.TransferApiApplication;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import java.io.IOException;

@CucumberContextConfiguration
@SpringBootTest(classes = TransferApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {

    private static final Gson GSON = new Gson();

    @Autowired
    protected TestRestTemplate restTemplate;

    public RestResponseTest latestResponse;

    public void executeGet(String url) throws IOException {
        latestResponse = restTemplate.execute(url, HttpMethod.GET, null, response -> new RestResponseTest(response, response.getStatusCode()));
    }

    public void executePost(String url, Object body) throws IOException {
        String json = GSON.toJson(body);
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(json);
        latestResponse = restTemplate.execute(url, HttpMethod.POST, requestCallback, response -> new RestResponseTest(response, response.getStatusCode()));
    }

}
