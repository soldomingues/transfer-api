package br.com.solangedomingues.transferapi.service.integration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;

public class HeaderSettingRequestCallback implements RequestCallback {

    private String body;

    public HeaderSettingRequestCallback(final String postBody) {
        this.body = postBody;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        request.getBody().write(body.getBytes());
    }
}
