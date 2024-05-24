package com.micro.flow.mapper;

import com.micro.flow.domain.Account;
import com.micro.flow.dto.AccountResponse;
import com.micro.flow.dto.UserDtoForAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", source = "account.id")
    AccountResponse getResponseFromDomain(Account account, UserDtoForAccount userDtoForAccount);

}
