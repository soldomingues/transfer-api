package br.com.solangedomingues.transferapi.services;

import br.com.solangedomingues.transferapi.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<Client> findByNumber(Integer number);
    Client save(Client client);
    List<Client> findAll();
    Optional<Client> findById(long id);
}
