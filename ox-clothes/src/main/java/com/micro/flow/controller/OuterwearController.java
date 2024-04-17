package com.micro.flow.controller;

import com.micro.flow.dto.outerwear.OuterwearCreateRequest;
import com.micro.flow.dto.outerwear.OuterwearResponse;
import com.micro.flow.dto.outerwear.ShortenOuterwearResponse;
import com.micro.flow.mapper.OuterwearMapper;
import com.micro.flow.service.OuterwearService;
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
@RequestMapping("/api/v1/outerwear")
public class OuterwearController {
    private final OuterwearService outerwearService;
    private final OuterwearMapper outerwearMapper;

    @GetMapping
    public ResponseEntity<List<ShortenOuterwearResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "order", defaultValue = "-") String order,
            @RequestParam(value = "value", defaultValue = "") String searchValue,
            @RequestParam(name = "properties",
                    defaultValue = "availableAmount") String... properties) {
        var outerwears = outerwearService.getAllBySearch(page, order, searchValue, properties);
        log.info("GET-OUTERWEAR'S-ALL");

        return ok(outerwearMapper.getShortenResponseListFromDomainPage(outerwears));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OuterwearResponse> getById(@PathVariable("id") Long id) {
        var outerwear = outerwearService.getOneById(id);
        log.info("GET-OUTERWEAR by id: {}", id);

        return ok(outerwearMapper.getResponseFromDomain(outerwear));
    }

    @PostMapping
    public ResponseEntity<OuterwearResponse> createOuterwear(
            @RequestBody @Valid OuterwearCreateRequest createRequest) {
        var created = outerwearService.create(outerwearMapper.getDomainFromCreateRequest(createRequest));
        log.info("CREATE-OUTERWEAR: {}", created);

        return status(HttpStatus.CREATED)
                .body(outerwearMapper.getResponseFromDomain(created));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        outerwearService.delete(id);
        log.info("DELETE-OUTERWEAR with id: {}", id);
    }

}
