package br.com.solangedomingues.transferapi.entity;

import br.com.solangedomingues.transferapi.enums.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transfer implements Serializable {

    private static final long serialVersionUID = -3019218051268261784L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long originAccount;

    @NotNull
    private Long destinationAccount;

    @NotNull
    @Size(min = 0, max = 1000)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private Date date;

}
