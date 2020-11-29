package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transaction;
import br.com.solangedomingues.transferapi.enums.TransactionStatus;
import br.com.solangedomingues.transferapi.exception.*;
import br.com.solangedomingues.transferapi.repository.CustomerRepository;
import br.com.solangedomingues.transferapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Customer> saveCustomer(Customer customer) {
        if(customer.getBalance().compareTo(new BigDecimal(0))<0){
            throw new NegativeBalanceException("Balance cannot start negative.");
        }
        if(!customerRepository.findByAccountNumber(customer.getAccountNumber()).isEmpty()){
            throw new AccountNumberAlreadyRegisteredException("Account number already registered.");
        }
        return Optional.ofNullable(customerRepository.save(customer));
    }

    @Override
    public Optional<Customer> findByAccountNumber(Long accountNumber) {

        Optional<Customer> customer = customerRepository.findByAccountNumber(accountNumber);
        if (customer.isEmpty()) {
            throw new NotFoundException("Account Not Found.");
        }
        return customer;
    }

    @Override
    public List<Customer> findAllCostumers() {
        return customerRepository.findAll();
    }

    @Override
    public synchronized Optional<Transaction> saveTransaction(Transaction transaction) {
        Customer originCustomer = Optional.of(customerRepository.findByAccountNumber(transaction.getOriginAccount())).get().orElse(null);
        Customer destCustomer = Optional.of(customerRepository.findByAccountNumber(transaction.getDestinationAccount())).get().orElse(null);

        if (Objects.isNull(destCustomer) || Objects.isNull(originCustomer)) {
            transaction.setStatus(TransactionStatus.ACCOUNT_NOT_EXISTS);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            throw new NotFoundException("Account not registered.");
        }

        if (originCustomer.getBalance().compareTo(transaction.getValue()) < 0) {
            transaction.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            throw new NegativeBalanceException("Insufficient Balance.");
        }

        if (transaction.getValue().compareTo(new BigDecimal(1000)) > 0) {
            transaction.setStatus(TransactionStatus.EXCEEDED_TRANSACTION_VALUE);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            throw new ExceedecTransactionValueException("Exceeded Transaction Value.");
        }

        if (transaction.getValue().compareTo(new BigDecimal(0))  < 0) {
            transaction.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            transaction.setDate(new Date());
            transactionRepository.save(transaction);
            throw new NegativeTransactionValueException("Negative Transaction Value.");
        }
        originCustomer.setBalance(originCustomer.getBalance().subtract(transaction.getValue()));
        destCustomer.setBalance(destCustomer.getBalance().add(transaction.getValue()));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setDate(new Date());

        transactionRepository.save(transaction);
        customerRepository.save(originCustomer);
        customerRepository.save(destCustomer);

        return Optional.of(transaction);
    }

    @Override
    public List<Transaction> findAllTransactionsByAccount(Long accountNumber) {

        Optional<Customer> customer = customerRepository.findByAccountNumber(accountNumber);
        if (customer.isEmpty()) {
            throw new NotFoundException("Account Not Found.");
        }
        return transactionRepository.findAllByAccount(accountNumber);
    }
}
