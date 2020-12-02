package br.com.solangedomingues.transferapi.dto;

import br.com.solangedomingues.transferapi.entity.Transfer;
import br.com.solangedomingues.transferapi.enums.TransferStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO  implements Serializable {

    private static final long serialVersionUID = -1890834422012890145L;

    @Hidden
    private Long id;

    @NotNull
    private Long originAccount;

    @NotNull
    private Long destinationAccount;

    @NotNull
    @Size(min = 0, max = 1000)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Hidden
    private TransferStatus status;

    @Hidden
    private Date date;

    public TransferDTO(Transfer transfer){
        this.id = transfer.getId();
        this.originAccount = transfer.getOriginAccount();
        this.destinationAccount = transfer.getDestinationAccount();
        this.value = transfer.getValue();
        this.status = transfer.getStatus();
        this.date = transfer.getDate();
    }

    public static List<TransferDTO> converter(List<Transfer> transfer) {
        return transfer.stream().map(TransferDTO::new).collect(Collectors.toList());
    }
}
