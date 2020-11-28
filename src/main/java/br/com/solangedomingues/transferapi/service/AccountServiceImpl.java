package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transaction;
import br.com.solangedomingues.transferapi.enums.TransactionStatus;
import br.com.solangedomingues.transferapi.repository.CustomerRepository;
import br.com.solangedomingues.transferapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Customer> findByAccountNumber(Long accountNumber) {
        return customerRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAllCostumers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findByIdCostumer(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> findByIdTransaction(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        Customer originCustomer = Optional.of(customerRepository.findByAccountNumber(transaction.getOriginAccount())).get().orElse(null);
        Customer destCustomer = Optional.of(customerRepository.findByAccountNumber(transaction.getDestinationAccount())).get().orElse(null);

        if (Objects.isNull(destCustomer) || Objects.isNull(originCustomer)) {
            //TODO ERRO
            transaction.setStatus(TransactionStatus.ACCOUNT_NOT_EXISTS);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            return null;
        }

        if (originCustomer.getBalance().compareTo(transaction.getValue()) < 0) {
            //TODO ERRO
            transaction.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            return null;
        }

        if(transaction.getValue().compareTo(new BigDecimal(1000)) > 0){
            //TODO ERRO
            transaction.setStatus(TransactionStatus.EXCEEDED_TRANSACTION_VALUE);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            return null;
        }

        originCustomer.setBalance(originCustomer.getBalance().subtract(transaction.getValue()));
        destCustomer.setBalance(destCustomer.getBalance().add(transaction.getValue()));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setDate(new Date());

        transactionRepository.save(transaction);
        saveCustomer(originCustomer);
        saveCustomer(destCustomer);

        return transaction;
    }

    @Override
    public List<Transaction> findAllTransactionsByAccount(Long accountNumber) {
        return transactionRepository.findAllByAccount(accountNumber);
    }
}
