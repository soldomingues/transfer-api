package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Optional;

public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public Response(Situation situation) {
        this.situation = situation;
    }

    public Response(Object result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public Response() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
