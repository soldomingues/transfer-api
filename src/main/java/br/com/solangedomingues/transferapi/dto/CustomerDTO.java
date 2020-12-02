package br.com.solangedomingues.transferapi.dto;

import br.com.solangedomingues.transferapi.entity.Customer;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO  implements Serializable {

    private static final long serialVersionUID = -4974011119978425569L;

    @Hidden
    private Long id;

    @NotNull
    private Long accountNumber;

    @NotBlank
    @Size(min = 0, max = 50)
    private String name;

    @NotNull
    private BigDecimal balance;

    public CustomerDTO(Customer customer){
        this.id = customer.getId();
        this.accountNumber = customer.getAccountNumber();
        this.name = customer.getName();
        this.balance = customer.getBalance();
    }

    public static List<CustomerDTO> converter(List<Customer> customer) {
        return customer.stream().map(CustomerDTO::new).collect(Collectors.toList());
    }
}
