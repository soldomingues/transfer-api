package br.com.solangedomingues.transferapi.service.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import br.com.solangedomingues.transferapi.vo.Response;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

public class RestResponseTest {
    private final Response response;
    private final HttpStatus statusCode;

    RestResponseTest(final ClientHttpResponse response, final HttpStatus statusCode) throws IOException {
        this.statusCode = statusCode;
        final InputStream bodyInputStream = response.getBody();
        final StringWriter stringWriter = new StringWriter();
        IOUtils.copy(bodyInputStream, stringWriter);
        this.response = new Gson().fromJson(stringWriter.toString(), Response.class);
    }

    public Response getResponse() {
        return response;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
