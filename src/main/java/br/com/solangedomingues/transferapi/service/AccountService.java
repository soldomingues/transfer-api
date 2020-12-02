package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.dto.CustomerDTO;
import br.com.solangedomingues.transferapi.dto.TransferDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<CustomerDTO> saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> findAllCostumers();
    Optional<CustomerDTO> findByAccountNumber(Long accountNumber);

    Optional<TransferDTO> makeTransfer(TransferDTO transferDTO);
    List<TransferDTO> findAllTransfersByAccount(Long accountNumber);
}
