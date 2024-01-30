package com.easybank.accounts.service;

import com.easybank.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto - customer dto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input mobile number
     * @return Account details based on Mobile number
     */
    CustomerDto fetchAccount(String mobileNumber);
}
