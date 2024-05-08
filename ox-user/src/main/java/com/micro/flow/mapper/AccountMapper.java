package com.micro.flow.mapper;

import com.micro.flow.domain.Account;
import com.micro.flow.dto.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse getResponseFromDomain(Account account);

}