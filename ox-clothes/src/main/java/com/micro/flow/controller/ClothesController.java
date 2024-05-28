package com.micro.flow.controller;

import com.micro.flow.dto.ClothesCreateRequest;
import com.micro.flow.dto.ClothesResponse;
import com.micro.flow.dto.ShortenClothesResponse;
import com.micro.flow.mapper.ClothesMapper;
import com.micro.flow.service.ClothesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clothes")
@PreAuthorize("hasAnyRole('ox_user', 'ox_admin')")
public class ClothesController {
    private final ClothesService clothesService;
    private final ClothesMapper clothesMapper;

    @GetMapping
    public ResponseEntity<List<ShortenClothesResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "order", defaultValue = "-") String order,
            @RequestParam(value = "value", defaultValue = "") String searchValue,
            @RequestParam(name = "properties",
                    defaultValue = "availableAmount") String... properties) {
        var clothes = clothesService.getAllBySearch(page, order, searchValue, properties);
        log.debug("GET-CLOTHES-ALL");

        return ok(clothesMapper.getShortenResponseListFromDomainPage(clothes));
    }

    @GetMapping("/for-bag/ids")
    public ResponseEntity<List<ShortenClothesResponse>> getAllByIds(
            @RequestBody List<Long> ids) {
        var clothes = clothesService.getAllByIds(ids);
        log.debug("GET-CLOTHES by ids: {}", ids);

        return ok(clothesMapper.getShortenResponseListFromDomain(clothes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClothesResponse> getById(@PathVariable("id") Long id) {
        var clothes = clothesService.getOneById(id);
        log.debug("GET-CLOTHES by id: {}", id);

        return ok(clothesMapper.getResponseFromDomain(clothes));
    }

    @PostMapping
    @PreAuthorize("hasRole('ox_seller')")
    public ResponseEntity<ClothesResponse> createClothes(
            @RequestBody @Valid ClothesCreateRequest createRequest) {
        var created = clothesService.create(
                clothesMapper.getDomainFromCreateRequest(createRequest));
        log.debug("CREATE-CLOTHES: {}", created);

        return status(HttpStatus.CREATED)
                .body(clothesMapper.getResponseFromDomain(created));
    }

    @DeleteMapping("/seller/{username}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@clothesAuthService.isUserAuthAndOwnerOfClothes(#username, authentication.name, #id)")
    public void delete(@PathVariable("id") Long id, @PathVariable String username) {
        clothesService.delete(id);
        log.debug("DELETE-CLOTHES with id: {}", id);
    }

    @GetMapping("/for-bag/total-price")
    public ResponseEntity<BigDecimal> getTotalPriceForBag(@RequestBody List<Long> ids) {
        var totalPrice = clothesService.reduceTotalPriceForBag(ids);
        log.debug("REDUCE-CLOTHES_FOR_BAG by ids: {}", ids);

        return ok(totalPrice);
    }
}
