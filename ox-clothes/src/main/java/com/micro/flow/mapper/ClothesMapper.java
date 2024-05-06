package com.micro.flow.mapper;

import com.micro.flow.domain.Clothes;
import com.micro.flow.dto.ClothesCreateRequest;
import com.micro.flow.dto.ClothesResponse;
import com.micro.flow.dto.ShortenClothesResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClothesMapper {

    @Mapping(target = "availableColors", expression = "java(clothes.getColors().size())")
    @Mapping(target = "availableSizes", expression = "java(clothes.getSizes().stream()\n" +
            "                .map(sampleSize -> sampleSize.name())\n" +
            "                .toList())")
    ShortenClothesResponse getShortenResponseFromDomain(Clothes clothes);

    List<ShortenClothesResponse> getShortenResponseListFromDomain(List<Clothes> clothes);

    default List<ShortenClothesResponse> getShortenResponseListFromDomainPage(Page<Clothes> page) {
        return getShortenResponseListFromDomain(page.getContent());
    }

    @Mapping(target = "availableSizes", expression = "java(clothes.getSizes().stream()\n" +
            "                .map(sampleSize -> sampleSize.name())\n" +
            "                .toList())")
    ClothesResponse getResponseFromDomain(Clothes clothes);

    Clothes getDomainFromCreateRequest(ClothesCreateRequest createRequest);
}
