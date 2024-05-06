package com.micro.flow.mapper;

import com.micro.flow.domain.Bag;
import com.micro.flow.dto.BagResponse;
import org.springframework.stereotype.Component;

@Component
public interface BagMapper {

    BagResponse getBagResponseFromDomain(Bag bag);

}
