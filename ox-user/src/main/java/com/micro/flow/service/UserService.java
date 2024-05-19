package com.micro.flow.service;

import com.micro.flow.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User createWithBag(User user, String password);

    User readByUsername(String username);

}
