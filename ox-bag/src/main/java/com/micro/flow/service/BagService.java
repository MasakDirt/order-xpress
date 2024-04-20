package com.micro.flow.service;

import com.micro.flow.domain.Bag;
import org.springframework.stereotype.Service;

@Service
public interface BagService {

    Bag create(String userEmail);

}
