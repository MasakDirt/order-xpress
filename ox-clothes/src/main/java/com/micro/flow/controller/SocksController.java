package com.micro.flow.controller;

import com.micro.flow.dto.socks.ShortenSocksResponse;
import com.micro.flow.dto.socks.SocksCreateRequest;
import com.micro.flow.dto.socks.SocksResponse;
import com.micro.flow.mapper.SocksMapper;
import com.micro.flow.service.SocksService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/socks")
public class SocksController {
    private final SocksService socksService;
    private final SocksMapper socksMapper;

    @GetMapping
    public ResponseEntity<List<ShortenSocksResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "order", defaultValue = "-") String order,
            @RequestParam(value = "value", defaultValue = "") String searchValue,
            @RequestParam(name = "properties", defaultValue = "price") String... properties) {
        var socks = socksService.getAllBySearch(page, order, searchValue, properties);
        log.info("GET-SOCKS-ALL");

        return ok(socksMapper.getShortenSocksResponseListFromDomainPage(socks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocksResponse> getById(@PathVariable("id") Long id) {
        var socks = socksService.getOneById(id);
        log.info("GET-SOCKS with id: {}", id);

        return ok(socksMapper.getResponseFromDomain(socks));
    }

    @PostMapping
    public ResponseEntity<SocksResponse> create(
            @RequestBody @Valid SocksCreateRequest createRequest) {
        var socks = socksService.create(socksMapper.getDomainFromCreateRequest(createRequest));
        log.info("POST-SOCKS with id: {}", socks.getId());

        return ok(socksMapper.getResponseFromDomain(socks));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        socksService.delete(id);
        log.info("DELETE-SOCKS with id: {}", id);
    }

}
