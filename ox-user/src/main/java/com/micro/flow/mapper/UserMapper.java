package com.micro.flow.mapper;

import com.micro.flow.domain.Role;
import com.micro.flow.domain.User;
import com.micro.flow.dto.UserCreateRequest;
import com.micro.flow.dto.UserDtoForAccount;
import com.micro.flow.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Role.class)
public interface UserMapper {

    @Mapping(target = "isEmailVerified", expression = "java(user.isEmailVerified())")
    UserResponse getUserResponseFromDomain(User user);

    User getUserFromCreateRequest(UserCreateRequest createRequest);

    UserDtoForAccount getUserDtoForAccountFromDomain(User user);

    default Set<String> getRolesNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
