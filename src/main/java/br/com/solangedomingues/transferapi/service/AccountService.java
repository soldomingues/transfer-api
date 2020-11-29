package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transfer;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Customer> saveCustomer(Customer customer);
    List<Customer> findAllCostumers();
    Optional<Customer> findByAccountNumber(Long accountNumber);

    Optional<Transfer> makeTransfer(Transfer transfer);
    List<Transfer> findAllTransfersByAccount(Long accountNumber);
}
