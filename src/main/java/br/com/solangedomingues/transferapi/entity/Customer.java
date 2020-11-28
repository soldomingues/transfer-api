package br.com.solangedomingues.transferapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long accountNumber;

    private String name;

    private BigDecimal balance;

//    @OneToMany(mappedBy = "origin")
//    @JsonIgnore
//    private Set<Transaction> originTransaction;
//
//    @OneToMany(mappedBy = "destination")
//    @JsonIgnore
//    private Set<Transaction> destinationTransaction;

    public Customer() {
    }

    public Customer(Long id, Long accountNumber, String name, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
