package com.micro.flow.service.auth;

import com.micro.flow.service.ClothesService;
import com.micro.flow.service.GlobalAuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClothesAuthService {
    private final GlobalAuthService globalAuthService;
    private final ClothesService clothesService;

    public boolean isUserAuthAndOwnerOfClothes(String passedUsername, String authUsername, Long id) {
        return globalAuthService.isUserAuthenticated(passedUsername, authUsername) &&
                clothesService.getOneById(id).getSellerUsername().equals(passedUsername);
    }
}
