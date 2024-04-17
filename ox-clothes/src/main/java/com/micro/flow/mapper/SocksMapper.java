package com.micro.flow.mapper;

import com.micro.flow.domain.Socks;
import com.micro.flow.dto.socks.ShortenSocksResponse;
import com.micro.flow.dto.socks.SocksCreateRequest;
import com.micro.flow.dto.socks.SocksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocksMapper {

    @Mapping(target = "availableColors", expression = "java(socks.getColors().size())")
    ShortenSocksResponse getShortenResponseFromDomain(Socks socks);

    List<ShortenSocksResponse> mapToShortenSocksResponseList(List<Socks> userList);

    default List<ShortenSocksResponse> getShortenSocksResponseListFromDomainPage(Page<Socks> userPage) {
        return mapToShortenSocksResponseList(userPage.getContent());
    }

    SocksResponse getResponseFromDomain(Socks socks);

    Socks getDomainFromCreateRequest(SocksCreateRequest socksCreateRequest);
}
