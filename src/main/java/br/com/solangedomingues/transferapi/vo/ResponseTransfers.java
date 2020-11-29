package br.com.solangedomingues.transferapi.vo;

import br.com.solangedomingues.transferapi.entity.Transfer;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class ResponseTransfers implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Transfer> result;

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Situation situation;

    public ResponseTransfers(Situation situation) {
        this.situation = situation;
    }

    public ResponseTransfers(List<Transfer> result, Situation situation) {
        this.result = result;
        this.situation = situation;
    }

    public ResponseTransfers() {
    }

    public List<Transfer> getResult() {
        return result;
    }

    public void setResult(List<Transfer> result) {
        this.result = result;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

}
