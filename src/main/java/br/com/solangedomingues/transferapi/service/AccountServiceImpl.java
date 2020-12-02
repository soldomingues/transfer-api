package br.com.solangedomingues.transferapi.service;

import br.com.solangedomingues.transferapi.dto.CustomerDTO;
import br.com.solangedomingues.transferapi.dto.TransferDTO;
import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transfer;
import br.com.solangedomingues.transferapi.enums.TransferStatus;
import br.com.solangedomingues.transferapi.exception.*;
import br.com.solangedomingues.transferapi.repository.CustomerRepository;
import br.com.solangedomingues.transferapi.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final TransferRepository transferRepository;

    public AccountServiceImpl(CustomerRepository customerRepository, TransferRepository transferRepository) {
        this.customerRepository = customerRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public Optional<CustomerDTO> saveCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer(null, customerDTO.getAccountNumber(), customerDTO.getName(), customerDTO.getBalance());

        if(customer.getBalance().compareTo(new BigDecimal(0))<0){
            logger.info("balance cannot start negative");
            throw new NegativeBalanceException("balance cannot start negative");
        }
        if(!customerRepository.findByAccountNumber(customer.getAccountNumber()).isEmpty()){
            logger.info("account number already registered");
            throw new AccountNumberAlreadyRegisteredException("account number already registered");
        }

        return Optional.of(new CustomerDTO(customerRepository.save(customer)));
    }

    @Override
    public Optional<CustomerDTO> findByAccountNumber(Long accountNumber) {

        Optional<CustomerDTO> customerDTO = customerRepository.findByAccountNumber(accountNumber);
        if (customerDTO.isEmpty()) {
            logger.info("account not found");
            throw new NotFoundException("account not found");
        }
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> findAllCostumers() {

        List<Customer> returnList = customerRepository.findAll();
        logger.info("returnList: {}", returnList);

        return CustomerDTO.converter(returnList);
    }

    @Override
    public synchronized Optional<TransferDTO> makeTransfer(TransferDTO transferDTO) {
        CustomerDTO originCustomerDTO = customerRepository.findByAccountNumber(transferDTO.getOriginAccount()).orElse(null);
        CustomerDTO destCustomerDTO = customerRepository.findByAccountNumber(transferDTO.getDestinationAccount()).orElse(null);

        Transfer transfer = new Transfer(null, transferDTO.getOriginAccount(), transferDTO.getDestinationAccount(), transferDTO.getValue(), null, null);

        if (Objects.isNull(destCustomerDTO) || Objects.isNull(originCustomerDTO)) {
            transfer.setStatus(TransferStatus.ACCOUNT_NOT_EXISTS);
            transfer.setDate(new Date());
            transferRepository.save(transfer);

            logger.info("account not found");
            throw new NotFoundException("account not found");
        }

        Customer originCustomer = new Customer(originCustomerDTO.getId(), originCustomerDTO.getAccountNumber(), originCustomerDTO.getName(), originCustomerDTO.getBalance());
        Customer destCustomer = new Customer(destCustomerDTO.getId(), destCustomerDTO.getAccountNumber(), destCustomerDTO.getName(), destCustomerDTO.getBalance());

        validateTransfer(transfer, originCustomer);

        originCustomer.subtractBalance(transfer.getValue());
        destCustomer.addBalance(transfer.getValue());

        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setDate(new Date());

        transferRepository.save(transfer);

        customerRepository.save(originCustomer);
        customerRepository.save(destCustomer);

        return Optional.of(new TransferDTO(transfer));
    }

    @Override
    public List<TransferDTO> findAllTransfersByAccount(Long accountNumber) {

        Optional<CustomerDTO> customerDTO = customerRepository.findByAccountNumber(accountNumber);

        if (customerDTO.isEmpty()) {
            logger.info("account not found");
            throw new NotFoundException("account not found");
        }

        List<Transfer> returnList = transferRepository.findAllByAccount(accountNumber);

        return TransferDTO.converter(returnList);
    }

    private void validateTransfer(Transfer transfer, Customer originCustomer) {

        if (originCustomer.getBalance().compareTo(transfer.getValue()) < 0) {
            transfer.setStatus(TransferStatus.INSUFFICIENT_BALANCE);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            logger.info("insufficient balance");
            throw new NegativeBalanceException("insufficient balance");
        }

        if (transfer.getValue().compareTo(new BigDecimal(1000)) > 0) {
            transfer.setStatus(TransferStatus.EXCEEDED_TRANSFER_VALUE);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            logger.info("exceeded transfer value");
            throw new ExceededTransferValueException("exceeded transfer value");
        }

        if (transfer.getValue().compareTo(new BigDecimal(0))  <= 0) {
            transfer.setStatus(TransferStatus.NEGATIVE_TRANSFER_VALUE);
            transfer.setDate(new Date());
            transferRepository.save(transfer);
            logger.info("value cannot be less than or equal to zero");
            throw new NegativeTransferValueException("value cannot be less than or equal to zero");
        }
    }
}
