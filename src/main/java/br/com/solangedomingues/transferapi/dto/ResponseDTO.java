package br.com.solangedomingues.transferapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = -3135583686369231502L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SituationDTO situationDTO;

    public ResponseDTO(SituationDTO situationDTO) {
        this.situationDTO = situationDTO;
    }

}
