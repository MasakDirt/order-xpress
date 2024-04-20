package com.micro.flow.service.iml;

import com.micro.flow.domain.Bag;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BagServiceImpl implements BagService {
    private final BagRepository bagRepository;

    @Override
    public Bag create(String userEmail) {
        var bag = new Bag();
        bag.setUserEmail(userEmail);

        return bagRepository.save(bag);
    }

}
