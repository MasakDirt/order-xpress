package com.micro.flow.mapper;

import com.micro.flow.domain.User;
import com.micro.flow.dto.UserCreateRequest;
import com.micro.flow.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = User.Role.class)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponse getUserResponseFromDomain(User user);

    User getUserFromCreateRequest(UserCreateRequest createRequest);

}
