package br.com.solangedomingues.transferapi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class Situation {

    @JsonProperty("code")
    private int codeMessage;

    @JsonProperty("message")
    private String dsMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uri;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("error")
    private List<String> errors;

    public Situation(int codeMessage, String dsMessage, Date date, String uri, List<String> errors) {
        this.codeMessage = codeMessage;
        this.dsMessage = dsMessage;
        this.date = date;
        this.uri = uri;
        this.errors = errors;
    }

    public Situation() {
    }

    public int getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(int codeMessage) {
        this.codeMessage = codeMessage;
    }

    public String getDsMessage() {
        return dsMessage;
    }

    public void setDsMessage(String dsMessage) {
        this.dsMessage = dsMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
