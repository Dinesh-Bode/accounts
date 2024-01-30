package com.easybank.accounts.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class AccountsDto {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
