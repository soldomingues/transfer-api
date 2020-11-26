package br.com.solangedomingues.transferapi.repository;

import br.com.solangedomingues.transferapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByNumber(Integer number);
}
