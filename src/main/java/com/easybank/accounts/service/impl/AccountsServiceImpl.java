package com.easybank.accounts.service.impl;

import com.easybank.accounts.constants.AccountsConstants;
import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.entity.Accounts;
import com.easybank.accounts.entity.Customer;
import com.easybank.accounts.exception.CustomerAlreadyExistsException;
import com.easybank.accounts.mapper.CustomerMapper;
import com.easybank.accounts.repository.AccountsRepository;
import com.easybank.accounts.repository.CustomerRepository;
import com.easybank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with mobile number "+customerDto.getMobileNumber() );
        }
        customer.setCreatedBy("Anonymous");
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     *
     * @param customer - Customer object
     * @return the new created account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 100000000L+ new Random().nextInt(900000000);

        newAccounts.setAccountNumber(randomAccountNumber);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        newAccounts.setCreatedAt(LocalDateTime.now());
        newAccounts.setCreatedBy("Anonymous");
        return newAccounts;
    }
}
