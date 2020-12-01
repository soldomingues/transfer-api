package br.com.solangedomingues.transferapi.entity;

import br.com.solangedomingues.transferapi.enums.TransferStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long originAccount;

    private Long destinationAccount;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date date;

}
