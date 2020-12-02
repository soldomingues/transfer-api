package br.com.solangedomingues.transferapi.service.unit.service;

import br.com.solangedomingues.transferapi.dto.CustomerDTO;
import br.com.solangedomingues.transferapi.dto.TransferDTO;
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
        CustomerDTO customerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(10));
        Customer customerReturn = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerReturn);

        accountService.saveCustomer(customerDTO);

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void whenCreatingCustomerWithBalanceLessThanZeroReturnNegativeBalanceException(){
        CustomerDTO customerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(-1));
        assertThrows(NegativeBalanceException.class, () -> accountService.saveCustomer(customerDTO));
    }

    @Test
    void whenCreatingCustomerAccountNumberThatAlreadyExistsReturnAccountNumberAlreadyRegisteredException(){
        CustomerDTO customerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(10));
        when(customerRepository.findByAccountNumber(customerDTO.getAccountNumber())).thenReturn(Optional.of(customerDTO));
        assertThrows(AccountNumberAlreadyRegisteredException.class, () -> accountService.saveCustomer(customerDTO));
    }

    @Test
    void whenLookingCustomerByAccountNumberThatNotExistReturnNotFoundException(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        Long accountNumber = customer.getAccountNumber();
        assertThrows(NotFoundException.class, () -> accountService.findByAccountNumber(accountNumber));
    }

    @Test
    void whenLookingCustomerByAccountNumberExistingReturnDataCustomer(){
        CustomerDTO customer = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(10));
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
        CustomerDTO originCustomerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(10));
        CustomerDTO destCustomerDTO = new CustomerDTO(2L, 1002L, "Pedro", new BigDecimal(100));

        when(customerRepository.findByAccountNumber(originCustomerDTO.getAccountNumber())).thenReturn(Optional.of(originCustomerDTO));
        when(customerRepository.findByAccountNumber(destCustomerDTO.getAccountNumber())).thenReturn(Optional.of(destCustomerDTO));

        TransferDTO transferDTO = new TransferDTO(null,1001L, 1002L, new BigDecimal(20), null, null);

        assertThrows(NegativeBalanceException.class, () -> accountService.makeTransfer(transferDTO));

    }

    @Test
    void whenTransferringValidateIfTheAccountNumberExistsReturnNotFoundException(){
        TransferDTO transferDTO = new TransferDTO(null,1001L, 1007L, new BigDecimal(20), null, null);
        CustomerDTO originCustomerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(10));

        when(customerRepository.findByAccountNumber(originCustomerDTO.getAccountNumber())).thenReturn(Optional.of(originCustomerDTO));

        assertThrows(NotFoundException.class, () -> accountService.makeTransfer(transferDTO));

    }

    @Test
    void whenTransferringValidateIfExceededTransferValueReturnExceededTransferValueException(){
        CustomerDTO originCustomerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(10000));
        CustomerDTO destCustomerDTO = new CustomerDTO(2L, 1002L, "Pedro", new BigDecimal(10));

        when(customerRepository.findByAccountNumber(originCustomerDTO.getAccountNumber())).thenReturn(Optional.of(originCustomerDTO));
        when(customerRepository.findByAccountNumber(destCustomerDTO.getAccountNumber())).thenReturn(Optional.of(destCustomerDTO));

        TransferDTO transferDTO = new TransferDTO(null,1001L, 1002L, new BigDecimal(2000), null, null);

        assertThrows(ExceededTransferValueException.class, () -> accountService.makeTransfer(transferDTO));

    }

    @Test
    void whenTransferringValidateIfNegativeTransferValueReturnNegativeTransferValueException(){
        CustomerDTO originCustomerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(20000));
        CustomerDTO destCustomerDTO = new CustomerDTO(2L, 1002L, "Pedro", new BigDecimal(100));

        when(customerRepository.findByAccountNumber(originCustomerDTO.getAccountNumber())).thenReturn(Optional.of(originCustomerDTO));
        when(customerRepository.findByAccountNumber(destCustomerDTO.getAccountNumber())).thenReturn(Optional.of(destCustomerDTO));

        TransferDTO transferNegative = new TransferDTO(null,1001L, 1002L, new BigDecimal(-1), null, null);

        TransferDTO transferZero = new TransferDTO(null,1001L, 1002L, new BigDecimal(0), null, null);

        assertThrows(NegativeTransferValueException.class, () -> accountService.makeTransfer(transferNegative));
        assertThrows(NegativeTransferValueException.class, () -> accountService.makeTransfer(transferZero));

    }

    @Test
    void whenFindTransfersByAccountNumberReturnListTransfer(){
        Transfer transferOne = new Transfer(1L,1001L, 1002L, new BigDecimal(1), null, null);
        Transfer transferTwo = new Transfer(2L,1001L, 1002L, new BigDecimal(10), null, null);
        CustomerDTO customerDTO = new CustomerDTO(1L, 1001L, "Maria", new BigDecimal(20000));

        List<Transfer> listTransfers = new ArrayList<>();
        listTransfers.add(transferOne);
        listTransfers.add(transferTwo);

        when(transferRepository.findAllByAccount(customerDTO.getAccountNumber())).thenReturn(listTransfers);

        when(customerRepository.findByAccountNumber(customerDTO.getAccountNumber())).thenReturn(Optional.of(customerDTO));

        accountService.findAllTransfersByAccount(customerDTO.getAccountNumber());

        verify(transferRepository, times(1)).findAllByAccount(customerDTO.getAccountNumber());

    }

    @Test
    void whenFindTransfersByAccountNumberThatNotExistReturnNotFoundException(){
        Customer customer = new Customer(1L, 1001L, "Maria", new BigDecimal(10));
        Long accountNumber = customer.getAccountNumber();
        assertThrows(NotFoundException.class, () -> accountService.findByAccountNumber(accountNumber));
    }
}
