package br.com.solangedomingues.transferapi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Situation {

    @JsonProperty("codigo")
    private int codeMessage;

    @JsonProperty("message")
    private String dsMessage;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uri;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("error")
    private List<String> errors;

}
