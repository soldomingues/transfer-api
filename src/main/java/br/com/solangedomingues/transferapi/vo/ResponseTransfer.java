package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Transfer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Optional;

public class ResponseTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<Transfer> result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public ResponseTransfer(Situation situation) {
        this.situation = situation;
    }

    public ResponseTransfer(Optional<Transfer> result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public ResponseTransfer() {
    }

    public Optional<Transfer> getResult() {
        return result;
    }

    public void setResult(Optional<Transfer> result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
