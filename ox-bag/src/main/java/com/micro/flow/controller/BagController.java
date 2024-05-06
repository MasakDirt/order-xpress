package com.micro.flow.controller;

import com.micro.flow.dto.BagResponse;
import com.micro.flow.mapper.BagMapper;
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
    private final BagMapper bagMapper;

    @PostMapping("/{user-email}")
    public ResponseEntity<UUID> create(@PathVariable("user-email") String email) {
        var bag = bagService.create(email);
        log.debug("CREATED-BAG: {} - for user - {}", bag, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bag.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BagResponse> getById(@PathVariable("id") UUID id) {
        var response = bagMapper.getBagResponseFromDomain(bagService.getById(id));
        log.debug("GET BAG: by id - {}", id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/clothes/{clothes-id}")
    public ResponseEntity<String> addClothesToBag(
            @PathVariable("id") UUID id, @PathVariable("clothes-id") Long clothesId) {
        bagService.putClothesToBag(id, clothesId);
        log.debug("PUT-BAG === id - {} == clothes: {}", id, clothesId);

        return ResponseEntity.ok("You added new clothe to your bag!");
    }

    @DeleteMapping("/{id}/clothes/{clothes-id}")
    public ResponseEntity<String> deleteClothesFromBag(
            @PathVariable("id") UUID id, @PathVariable("clothes-id") Long clothesId) {
        bagService.deleteClothesFromBag(id, clothesId);
        log.debug("DELETE-BAG === id - {} == clothes: {}", id, clothesId);

        return ResponseEntity.ok("You deleted clothe from your bag!");
    }

}
