package com.micro.flow.component;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@Component
public class ServiceAdvisor {

    public static PageRequest getPageRequest(int page, int pageSize, String order, String[] properties) {
       return PageRequest.of(page, pageSize,
                by(getDirection(order), properties));
    }

    private static Sort.Direction getDirection(String order) {
        return switch (order) {
            case "+" -> ASC;
            case "-" -> DESC;
            default -> throw new IllegalArgumentException("Invalid order: " + order);
        };
    }

}
