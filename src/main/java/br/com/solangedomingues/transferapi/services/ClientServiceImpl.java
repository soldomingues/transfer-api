package br.com.solangedomingues.transferapi.services;

import br.com.solangedomingues.transferapi.entity.Client;
import br.com.solangedomingues.transferapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> findByNumber(Integer number) {
        return clientRepository.findByNumber(number);
    }

    @Override
    public Client save(Client client){
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }
}
