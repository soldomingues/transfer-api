package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class ResponseTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Transaction> result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public ResponseTransactions(Situation situation) {
        this.situation = situation;
    }

    public ResponseTransactions(List<Transaction> result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public ResponseTransactions() {
    }

    public List<Transaction> getResult() {
        return result;
    }

    public void setResult(List<Transaction> result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
