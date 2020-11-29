package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Optional;

public class ResponseTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<Transaction> result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public ResponseTransaction(Situation situation) {
        this.situation = situation;
    }

    public ResponseTransaction(Optional<Transaction> result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public ResponseTransaction() {
    }

    public Optional<Transaction> getResult() {
        return result;
    }

    public void setResult(Optional<Transaction> result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
