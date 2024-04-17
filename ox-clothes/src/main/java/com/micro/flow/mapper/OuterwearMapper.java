package com.micro.flow.mapper;

import com.micro.flow.domain.Outerwear;
import com.micro.flow.dto.outerwear.OuterwearCreateRequest;
import com.micro.flow.dto.outerwear.OuterwearResponse;
import com.micro.flow.dto.outerwear.ShortenOuterwearResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OuterwearMapper {

    @Mapping(target = "availableColors", expression = "java(outerwear.getColors().size())")
    @Mapping(target = "availableSizes", expression = "java(outerwear.getSizes().stream()\n" +
            "                .map(sampleSize -> sampleSize.name())\n" +
            "                .toList())")
    ShortenOuterwearResponse getShortenResponseFromDomain(Outerwear outerwear);

    List<ShortenOuterwearResponse> getShortenResponseListFromDomain(List<Outerwear> outerwears);

    default List<ShortenOuterwearResponse> getShortenResponseListFromDomainPage(Page<Outerwear> page) {
        return getShortenResponseListFromDomain(page.getContent());
    }

    @Mapping(target = "availableSizes", expression = "java(outerwear.getSizes().stream()\n" +
            "                .map(sampleSize -> sampleSize.name())\n" +
            "                .toList())")
    OuterwearResponse getResponseFromDomain(Outerwear outerwear);

    Outerwear getDomainFromCreateRequest(OuterwearCreateRequest createRequest);
}
