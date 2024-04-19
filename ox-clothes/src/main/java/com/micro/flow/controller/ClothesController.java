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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clothes")
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
        log.info("GET-CLOTHES-ALL");

        return ok(clothesMapper.getShortenResponseListFromDomainPage(clothes));
    }

    @GetMapping("/for-bag/{ids}")
    public ResponseEntity<List<ShortenClothesResponse>> getAllByIds(@PathVariable("ids") List<Long> ids) {
        var clothes = clothesService.getAllByIds(ids);
        log.info("GET-CLOTHES by ids: {}", ids);

        return ok(clothesMapper.getShortenResponseListFromDomain(clothes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClothesResponse> getById(@PathVariable("id") Long id) {
        var outerwear = clothesService.getOneById(id);
        log.info("GET-CLOTHES by id: {}", id);

        return ok(clothesMapper.getResponseFromDomain(outerwear));
    }

    @PostMapping
    public ResponseEntity<ClothesResponse> createOuterwear(
            @RequestBody @Valid ClothesCreateRequest createRequest) {
        var created = clothesService.create(clothesMapper.getDomainFromCreateRequest(createRequest));
        log.info("CREATE-CLOTHES: {}", created);

        return status(HttpStatus.CREATED)
                .body(clothesMapper.getResponseFromDomain(created));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        clothesService.delete(id);
        log.info("DELETE-CLOTHES with id: {}", id);
    }

}
