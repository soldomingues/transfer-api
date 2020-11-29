package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class ResponseCustomers implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Customer>  result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public ResponseCustomers(Situation situation) {
        this.situation = situation;
    }

    public ResponseCustomers(List<Customer> result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public List<Customer> getResult() {
        return result;
    }

    public void setResult(List<Customer> result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }
}
