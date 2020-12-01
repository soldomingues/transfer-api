package br.com.solangedomingues.transferapi.service.unit.service;

import br.com.solangedomingues.transferapi.entity.Customer;
import br.com.solangedomingues.transferapi.entity.Transfer;
import br.com.solangedomingues.transferapi.exception.*;
import br.com.solangedomingues.transferapi.repository.CustomerRepository;
import br.com.solangedomingues.transferapi.repository.TransferRepository;
import br.com.solangedomingues.transferapi.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
class AccountServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    TransferRepository transferRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void whenSendNewCustomerWithCorrectDataReturnSavedCustomer(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        accountService.saveCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void whenCreatingCustomerWithBalanceLessThanZeroReturnNegativeBalanceException(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(-1));
        assertThrows(NegativeBalanceException.class, () -> accountService.saveCustomer(customer));
    }

    @Test
    void whenCreatingCustomerAccountNumberThatAlreadyExistsReturnAccountNumberAlreadyRegisteredException(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        when(customerRepository.findByAccountNumber(customer.getAccountNumber())).thenReturn(Optional.of(customer));
        assertThrows(AccountNumberAlreadyRegisteredException.class, () -> accountService.saveCustomer(customer));
    }

    @Test
    void whenLookingCustomerByAccountNumberThatNotExistReturnNotFoundException(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        Long accountNumber = customer.getAccountNumber();
        assertThrows(NotFoundException.class, () -> accountService.findByAccountNumber(accountNumber));
    }

    @Test
    void whenLookingCustomerByAccountNumberExistingReturnDataCustomer(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        when(customerRepository.findByAccountNumber(customer.getAccountNumber())).thenReturn(Optional.of(customer));
        accountService.findByAccountNumber(customer.getAccountNumber());
        verify(customerRepository, times(1)).findByAccountNumber(customer.getAccountNumber());
    }

    @Test
    void whenFindAllCustomerReturnListAllCustomers(){
        accountService.findAllCostumers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void whenTransferringValidateThatTheBalanceOfTheOriginAccountIsSufficientReturnNegativeBalanceException(){
        Customer originCustomer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        Customer destCustomer = new Customer(2L, 1002L, "Pedro", new BigDecimal(100));

        when(customerRepository.findByAccountNumber(originCustomer.getAccountNumber())).thenReturn(Optional.of(originCustomer));
        when(customerRepository.findByAccountNumber(destCustomer.getAccountNumber())).thenReturn(Optional.of(destCustomer));

        Transfer transfer = new Transfer(null,1001L, 1002L, new BigDecimal(20), null, null);

        assertThrows(NegativeBalanceException.class, () -> accountService.makeTransfer(transfer));

    }

    @Test
    void whenTransferringValidateIfTheAccountNumberExistsReturnNotFoundException(){
        Transfer transfer = new Transfer(null,1001L, 1007L, new BigDecimal(20), null, null);

        assertThrows(NotFoundException.class, () -> accountService.makeTransfer(transfer));

    }

    @Test
    void whenTransferringValidateIfExceededTransferValueReturnExceededTransferValueException(){
        Customer originCustomer = new Customer(1L, 1001L, "Maria", new BigDecimal(10000));
        Customer destCustomer = new Customer(2L, 1002L, "Pedro", new BigDecimal(10));

        when(customerRepository.findByAccountNumber(originCustomer.getAccountNumber())).thenReturn(Optional.of(originCustomer));
        when(customerRepository.findByAccountNumber(destCustomer.getAccountNumber())).thenReturn(Optional.of(destCustomer));

        Transfer transfer = new Transfer(null,1001L, 1002L, new BigDecimal(2000), null, null);

        assertThrows(ExceededTransferValueException.class, () -> accountService.makeTransfer(transfer));

    }

    @Test
    void whenTransferringValidateIfNegativeTransferValueReturnNegativeTransferValueException(){
        Customer originCustomer = new Customer(1L, 1001L, "Maria", new BigDecimal(20000));
        Customer destCustomer = new Customer(2L, 1002L, "Pedro", new BigDecimal(100));

        when(customerRepository.findByAccountNumber(originCustomer.getAccountNumber())).thenReturn(Optional.of(originCustomer));
        when(customerRepository.findByAccountNumber(destCustomer.getAccountNumber())).thenReturn(Optional.of(destCustomer));

        Transfer transferNegative = new Transfer(null,1001L, 1002L, new BigDecimal(-1), null, null);

        Transfer transferZero = new Transfer(null,1001L, 1002L, new BigDecimal(0), null, null);

        assertThrows(NegativeTransferValueException.class, () -> accountService.makeTransfer(transferNegative));
        assertThrows(NegativeTransferValueException.class, () -> accountService.makeTransfer(transferZero));

    }

    @Test
    void whenFindTransfersByAccountNumberReturnListTransfer(){
        Transfer transferOne = new Transfer(1L,1001L, 1002L, new BigDecimal(1), null, null);
        Transfer transferTwo = new Transfer(2L,1001L, 1002L, new BigDecimal(10), null, null);
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(20000));

        List<Transfer> listTransfers = new ArrayList<>();
        listTransfers.add(transferOne);
        listTransfers.add(transferTwo);

        when(transferRepository.findAllByAccount(customer.getAccountNumber())).thenReturn(listTransfers);

        when(customerRepository.findByAccountNumber(customer.getAccountNumber())).thenReturn(Optional.of(customer));

        accountService.findAllTransfersByAccount(customer.getAccountNumber());

        verify(transferRepository, times(1)).findAllByAccount(customer.getAccountNumber());

    }

    @Test
    void whenFindTransfersByAccountNumberThatNotExistReturnNotFoundException(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        Long accountNumber = customer.getAccountNumber();
        assertThrows(NotFoundException.class, () -> accountService.findByAccountNumber(accountNumber));
    }
}
