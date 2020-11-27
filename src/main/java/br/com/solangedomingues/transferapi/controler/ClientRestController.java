package br.com.solangedomingues.transferapi.controler;

import br.com.solangedomingues.transferapi.entity.Client;
import br.com.solangedomingues.transferapi.exception.ClientNotFoundException;
import br.com.solangedomingues.transferapi.repository.ClientRepository;
import br.com.solangedomingues.transferapi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ClientRestController {

    @Autowired
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> createClient(@RequestBody Client client) {
        Client savedClient = clientService.save(client);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedClient.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/clients")
    public List<Client> retrieveAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client retrieveClients(@PathVariable long id) {
        Optional<Client> client = clientService.findById(id);

        if (!client.isPresent())
            throw new ClientNotFoundException("id-" + id);

        return client.get();
    }

    @GetMapping("/clients/number/{number}")
    public Client retrieveClientsForNumber(@PathVariable int number) {
        Optional<Client> client = clientService.findByNumber(number);

        if (!client.isPresent())
            throw new ClientNotFoundException("number-" + number);

        return client.get();
    }

}
