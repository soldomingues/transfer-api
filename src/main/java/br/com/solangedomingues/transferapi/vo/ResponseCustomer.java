package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Optional;

public class ResponseCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<Customer> result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public ResponseCustomer(Situation situation) {
        this.situation = situation;
    }

    public ResponseCustomer(Optional<Customer> result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public ResponseCustomer() {
    }

    public Optional<Customer> getResult() {
        return result;
    }

    public void setResult(Optional<Customer> result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
