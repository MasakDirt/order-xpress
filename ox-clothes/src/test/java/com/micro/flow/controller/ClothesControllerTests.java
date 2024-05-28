package com.micro.flow.controller;

import com.micro.flow.domain.Clothes;
import com.micro.flow.domain.SampleSize;
import com.micro.flow.domain.Type;
import com.micro.flow.dto.ClothesCreateRequest;
import com.micro.flow.dto.ClothesResponse;
import com.micro.flow.dto.ShortenClothesResponse;
import com.micro.flow.mapper.ClothesMapper;
import com.micro.flow.repository.ClothesRepository;
import com.micro.flow.service.ClothesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.micro.flow.ClothesTestingUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class ClothesControllerTests {
    private static final String BASIC_URL = "/api/v1/clothes";

    private final MockMvc mockMvc;
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final ClothesService clothesService;

    @Autowired
    public ClothesControllerTests(MockMvc mockMvc, ClothesRepository clothesRepository,
                                  ClothesMapper clothesMapper, ClothesService clothesService) {
        this.mockMvc = mockMvc;
        this.clothesRepository = clothesRepository;
        this.clothesMapper = clothesMapper;
        this.clothesService = clothesService;
    }

    @Test
    @WithMockUser(username = "davidos", roles = "ox_user")
    public void testGetAllSuccessWithEmptySearchValue() throws Exception {
        int page = 0;
        String order = "+";
        String searchValue = "";
        String properties = "price";
        int defaultPageSize = 4;

        List<Clothes> clothes = clothesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Clothes::getPrice))
                .limit(defaultPageSize)
                .toList();
        List<ShortenClothesResponse> expected =
                clothesMapper.getShortenResponseListFromDomain(clothes);

        performGetAllSuccess(page, order, searchValue, properties)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "markiz23", roles = {"ox_seller", "ox_user"})
    public void testGetAllInvalidOrder() throws Exception {
        int page = 0;
        String order = "invalid order";
        String searchValue = "Men";
        String properties = "price";

        performGetAllSuccess(page, order, searchValue, properties)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"BAD_REQUEST\",\"message\":" +
                                "\"Invalid order: invalid order\"")));
    }

    private ResultActions performGetAllSuccess(
            int page, String order, String searchValue, String properties) throws Exception {
        return mockMvc.perform(get(BASIC_URL)
                .param("page", String.valueOf(page))
                .param("order", order)
                .param("value", searchValue)
                .param("properties", properties));
    }

    @Test
    @WithMockUser(username = "adminito25", roles = {"ox_admin", "ox_user"})
    public void testGetAllByIdsSuccess() throws Exception {
        Set<Long> ids = Set.of(12L, 23L, 15L, 22L, 1L);

        List<ShortenClothesResponse> expected = clothesMapper.getShortenResponseListFromDomain(
                clothesRepository.findAllById(ids));

        performGetAllByIds(ids)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "stonisloff24", roles = "ox_user")
    public void testGetAllByIdsEmptyCollection() throws Exception {
        Set<Long> ids = Set.of();

        List<ShortenClothesResponse> expected = clothesMapper.getShortenResponseListFromDomain(
                clothesRepository.findAllById(ids));

        performGetAllByIds(ids)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    private ResultActions performGetAllByIds(Set<Long> ids) throws Exception {
        return mockMvc.perform(get(BASIC_URL + "/for-bag/ids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ids)));
    }

    @Test
    @WithMockUser(username = "stonisloff24", roles = "ox_user")
    public void testGetByIdSuccess() throws Exception {
        Long id = clothesRepository.findAll()
                .stream()
                .findFirst().orElseThrow().getId();

        ClothesResponse expected = clothesMapper.getResponseFromDomain(
                clothesService.getOneById(id));

        performGetOneById(id)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "stonisloff24", roles = "ox_user")
    public void testGetByIdNotFound() throws Exception {
        Long id = 0L;

        performGetOneById(id)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"NOT_FOUND\",\"message\":\"Clothes not found\"")));
    }

    private ResultActions performGetOneById(Long id) throws Exception {
        return mockMvc.perform(get(BASIC_URL + "/{id}", id));
    }

    @Test
    @WithMockUser(username = "markiz23", roles = {"ox_seller", "ox_user"})
    public void testCreateSuccess() throws Exception {
        ClothesCreateRequest request = createClothes();
        ClothesResponse expected = clothesMapper.getResponseFromDomain(
                clothesMapper.getDomainFromCreateRequest(request));
        expected.setId(clothesRepository.findAll().size() + 1L);

        performCreate(request)
                .andExpect(status().isCreated())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "markiz23", roles = {"ox_seller", "ox_user"})
    public void testCreateWithInvalidRequest() throws Exception {
        ClothesCreateRequest request = createClothes();
        request.setSex(null);

        performCreate(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"BAD_REQUEST\",\"message\":" +
                                "\"Sex must be included!\"")));
    }

    @Test
    @WithMockUser(username = "davidos", roles = "ox_user")
    public void testCreateWithForbidden() throws Exception {
        ClothesCreateRequest request = createClothes();

        performCreate(request)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ClothesCreateRequest createClothes() {
        return ClothesCreateRequest.builder()
                .sex("MALE")
                .price(BigDecimal.valueOf(120))
                .availableAmount(12)
                .productName("Colourful Jacket for Males")
                .description("Some jackets")
                .colors(List.of("colourful"))
                .type(Type.JACKET)
                .sizes(List.of(SampleSize.ONE_SIZE))
                .sellerUsername("markiz23")
                .build();
    }

    private ResultActions performCreate(ClothesCreateRequest createRequest) throws Exception {
        return mockMvc.perform(post(BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createRequest)));
    }

    @Test
    @WithMockUser(username = "markiz23", roles = {"ox_seller", "ox_user"})
    public void testDeleteSuccess() throws Exception {
        String username = "markiz23";
        Long id = 12L;

        performDelete(username, id)
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "markiz23", roles = {"ox_seller", "ox_user"})
    public void testDeleteNotFound() throws Exception {
        String username = "markiz23";
        Long id = 0L;

        performDelete(username, id)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("\"status\":\"NOT_FOUND\",\"message\":\"Clothes not found\"")));
    }

    @Test
    @WithMockUser(username = "markiz23", roles = {"ox_seller", "ox_user"})
    public void testDeleteForbiddenUsernameNotSame() throws Exception {
        String username = "adminito25";
        Long id = 2L;

        performDelete(username, id)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    @Test
    @WithMockUser(username = "davidos", roles = "ox_user")
    public void testDeleteForbiddenUserIsNotOwner() throws Exception {
        String username = "davidos";
        Long id = 2L;

        performDelete(username, id)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performDelete(String username, Long id) throws Exception {
        return mockMvc.perform(delete(BASIC_URL + "/seller/{username}/{id}", username, id));
    }

    private ResultMatcher getForbiddenStatus() {
        return status().isForbidden();
    }

    private ResultMatcher getForbiddenResult() {
        return result -> assertTrue(result.getResponse().getContentAsString()
                .contains("\"status\":\"FORBIDDEN\",\"message\":" +
                        "\"Access Denied\""));
    }

    @Test
    @WithMockUser(username = "stonisloff24", roles = "ox_user")
    public void testGetTotalPriceForBagSuccess() throws Exception {
        List<Long> ids = List.of(12L, 1L, 3L, 5L, 22L, 28L);
        BigDecimal expected = clothesService.reduceTotalPriceForBag(ids);

        mockMvc.perform(get(BASIC_URL + "/for-bag/total-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ids)))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

}
