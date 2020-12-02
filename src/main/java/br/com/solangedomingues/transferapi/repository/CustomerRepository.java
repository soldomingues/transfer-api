package br.com.solangedomingues.transferapi.repository;

import br.com.solangedomingues.transferapi.dto.CustomerDTO;
import br.com.solangedomingues.transferapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<CustomerDTO> findByAccountNumber(Long accountNumber);
}
