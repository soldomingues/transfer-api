package br.com.solangedomingues.transferapi.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(unique = true)
    private Long number;

    private String name;

    private Double balance;

    @OneToMany(mappedBy = "origin")
    private Set<Transaction> originTransaction;

    public Customer() {
        super();
    }

    public Customer(Long id, Long number, String name, Double balance) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.balance = balance;
    }

    public Set<Transaction> getOriginTransaction() {
        return originTransaction;
    }

    public void setOriginTransaction(Set<Transaction> originTransaction) {
        this.originTransaction = originTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
