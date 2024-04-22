package com.micro.flow.controller;

import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/my-bag")
public class BagController {
    private final BagService bagService;

    @PostMapping("/{user-email}")
    public ResponseEntity<UUID> create(@PathVariable("user-email") String email) {
        var bag = bagService.create(email);
        log.info("CREATED-BAG: {} - for user - {}", bag, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bag.getId());
    }

    @PutMapping("/{id}/clothes/{clothes-id}")
    public void addClothesToBag(@PathVariable("id") UUID id, @PathVariable("clothes-id") Long clothesId) {
        bagService.putClothesToBag(id, clothesId);
        log.info("PUT-BAG === id - {} == clothes: {}", id, clothesId);
    }

    @DeleteMapping("/{id}/clothes/{clothes-id}")
    public void deleteClothesFromBag(@PathVariable("id") UUID id, @PathVariable("clothes-id") Long clothesId) {
        bagService.deleteClothesFromBag(id, clothesId);
        log.info("DELETE-BAG === id - {} == clothes: {}", id, clothesId);
    }

}
