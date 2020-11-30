package br.com.solangedomingues.transferapi.service.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

public class RestResponse {
    private final ClientHttpResponse response;
    private final String body;
    private final HttpStatus statusCode;

    RestResponse(final ClientHttpResponse response, final HttpStatus statusCode) throws IOException {
        this.response = response;
        this.statusCode = statusCode;
        final InputStream bodyInputStream = response.getBody();
        final StringWriter stringWriter = new StringWriter();
        IOUtils.copy(bodyInputStream, stringWriter);
        this.body = stringWriter.toString();
    }

    public ClientHttpResponse getResponse() {
        return getResponse();
    }

    public String getBody() {
        return body;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
