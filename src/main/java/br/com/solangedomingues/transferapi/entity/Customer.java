package br.com.solangedomingues.transferapi.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @Column(unique = true)
    @NotNull
    private Long accountNumber;

    @NotBlank
    @Size(min = 0, max = 50)
    private String name;

    private BigDecimal balance;

    public void subtractBalance(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }

    public void addBalance(BigDecimal value) {
        this.balance = this.balance.add(value);
    }
}
