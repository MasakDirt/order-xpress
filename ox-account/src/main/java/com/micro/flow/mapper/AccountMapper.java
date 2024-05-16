package com.micro.flow.mapper;

import com.micro.flow.domain.Account;
import com.micro.flow.dto.AccountResponse;
import com.micro.flow.dto.UserDtoForAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse getResponseFromDomain(Account account, UserDtoForAccount userDtoForAccount);

}
