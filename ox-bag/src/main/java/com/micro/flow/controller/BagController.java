package com.micro.flow.controller;

import com.micro.flow.dto.BagResponse;
import com.micro.flow.mapper.BagMapper;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/my-bag")
public class BagController {
    private final BagService bagService;
    private final BagMapper bagMapper;

    @PostMapping("/{username}")
    public ResponseEntity<UUID> create(@PathVariable("username") String username) {
        var bag = bagService.create(username);
        log.debug("CREATED-BAG: {} - for user - {}", bag, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bag.getId());
    }

    @GetMapping("/{username}")
    @PreAuthorize("@globalAuthService.isUserAuthenticated(#username, authentication.name)")
    public ResponseEntity<BagResponse> getByUsername(@PathVariable("username") String username) {
        var response = bagMapper.getBagResponseFromDomain(bagService.getByUsername(username));
        log.debug("GET BAG: by username - {}", username);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}/clothes/{clothes-id}")
    @PreAuthorize("@globalAuthService.isUserAuthenticated(#username, authentication.name)")
    public ResponseEntity<String> addClothesToBag(
            @PathVariable("username") String username,
            @PathVariable("clothes-id") Long clothesId) {
        bagService.putClothesToBag(username, clothesId);
        log.debug("PUT-BAG === username - {} == clothes: {}", username, clothesId);

        return ResponseEntity.ok("You added new clothe to your bag!");
    }

    @DeleteMapping("/{username}/clothes/{clothes-id}")
    @PreAuthorize("@globalAuthService.isUserAuthenticated(#username, authentication.name)")
    public ResponseEntity<String> deleteClothesFromBag(
            @PathVariable("username") String username,
            @PathVariable("clothes-id") Long clothesId) {
        bagService.deleteClothesFromBag(username, clothesId);
        log.debug("DELETE-BAG === username - {} == clothes: {}", username, clothesId);

        return ResponseEntity.ok("You deleted clothe from your bag!");
    }

    @GetMapping("/{username}/total-price")
    @PreAuthorize("@globalAuthService.isUserAuthenticated(#username, authentication.name)")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable("username") String username) {
        var totalPrice = bagService.getTotalPriceByUsername(username);
        log.debug("GET-BAG-TOTAL_PRICE: username - {}", username);

        return ResponseEntity.ok(totalPrice);
    }

    @PostMapping("/{username}/reset-bag")
    @PreAuthorize("@globalAuthService.isUserAuthenticated(#username, authentication.name)")
    public void resetBag(@PathVariable("username") String username) {
        bagService.resetBag(username);
        log.debug("EMPTY-BAG: username - {}", username);
    }

}
