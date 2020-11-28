package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Customer> findByAccountNumber(Long accountNumber);
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCostumers();
    Optional<Customer> findByIdCostumer(Long id);

    List<Transaction> findAllTransactions();
    Optional<Transaction> findByIdTransaction(Long id);
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> findAllTransactionsByAccount(Long accountNumber);

}
