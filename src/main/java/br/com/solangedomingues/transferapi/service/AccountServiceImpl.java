package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transfer;
import br.com.solangedomingues.transferapi.enums.TransferStatus;
import br.com.solangedomingues.transferapi.exception.*;
import br.com.solangedomingues.transferapi.repository.CustomerRepository;
import br.com.solangedomingues.transferapi.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;

    private final TransferRepository transferRepository;

    public AccountServiceImpl(CustomerRepository customerRepository, TransferRepository transferRepository) {
        this.customerRepository = customerRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public Optional<Customer> saveCustomer(Customer customer) {
        if(customer.getBalance().compareTo(new BigDecimal(0))<0){
            throw new NegativeBalanceException("Balance cannot start negative");
        }
        if(!customerRepository.findByAccountNumber(customer.getAccountNumber()).isEmpty()){
            throw new AccountNumberAlreadyRegisteredException("Account number already registered");
        }
        return Optional.ofNullable(customerRepository.save(customer));
    }

    @Override
    public Optional<Customer> findByAccountNumber(Long accountNumber) {

        Optional<Customer> customer = customerRepository.findByAccountNumber(accountNumber);
        if (customer.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }
        return customer;
    }

    @Override
    public List<Customer> findAllCostumers() {
        return customerRepository.findAll();
    }

    @Override
    public synchronized Optional<Transfer> makeTransfer(Transfer transfer) {
        Customer originCustomer = customerRepository.findByAccountNumber(transfer.getOriginAccount()).orElse(null);
        Customer destCustomer = customerRepository.findByAccountNumber(transfer.getDestinationAccount()).orElse(null);

        validateTransfer(transfer, originCustomer, destCustomer);

        originCustomer.subtractBalance(transfer.getValue());
        destCustomer.addBalance(transfer.getValue());

        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setDate(new Date());

        transferRepository.save(transfer);

        customerRepository.save(originCustomer);
        customerRepository.save(destCustomer);

        return Optional.of(transfer);
    }

    @Override
    public List<Transfer> findAllTransfersByAccount(Long accountNumber) {

        Optional<Customer> customer = customerRepository.findByAccountNumber(accountNumber);
        if (customer.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }
        return transferRepository.findAllByAccount(accountNumber);
    }

    private void validateTransfer(Transfer transfer, Customer originCustomer, Customer destCustomer) {
        if (Objects.isNull(destCustomer) || Objects.isNull(originCustomer)) {
            transfer.setStatus(TransferStatus.ACCOUNT_NOT_EXISTS);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            throw new NotFoundException("Account not registered");
        }

        if (originCustomer.getBalance().compareTo(transfer.getValue()) < 0) {
            transfer.setStatus(TransferStatus.INSUFFICIENT_BALANCE);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            throw new NegativeBalanceException("Insufficient Balance");
        }

        if (transfer.getValue().compareTo(new BigDecimal(1000)) > 0) {
            transfer.setStatus(TransferStatus.EXCEEDED_TRANSFER_VALUE);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            throw new ExceededTransferValueException("Exceeded Transfer Value");
        }

        if (transfer.getValue().compareTo(new BigDecimal(0))  <= 0) {
            transfer.setStatus(TransferStatus.NEGATIVE_TRANSFER_VALUE);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            throw new NegativeTransferValueException("Value cannot be less than or equal to zero");
        }
    }
}
