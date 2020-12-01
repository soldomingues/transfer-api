package br.com.solangedomingues.transferapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Long id;

    @Column(unique = true)
    private Long accountNumber;

    private String name;

    private BigDecimal balance;

    public void subtractBalance(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }

    public void addBalance(BigDecimal value) {
        this.balance = this.balance.add(value);
    }
}
