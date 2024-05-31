package com.micro.flow.controller;

import com.micro.flow.domain.Bag;
import com.micro.flow.dto.BagResponse;
import com.micro.flow.mapper.BagMapper;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;

import static com.micro.flow.BagTestingUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class BagControllerTests {
    private static final String BASIC_URL = "/api/v1/my-bag";

    private final MockMvc mockMvc;
    private final BagService bagService;
    private final BagMapper bagMapper;
    @Autowired
    private BagRepository bagRepository;

    @Autowired
    public BagControllerTests(MockMvc mockMvc, BagService bagService, BagMapper bagMapper) {
        this.mockMvc = mockMvc;
        this.bagService = bagService;
        this.bagMapper = bagMapper;
    }

    @Test
    public void testCreateSuccess() throws Exception {
        String username = "newcreate_testing";

        performCreate(username)
                .andExpect(status().isCreated())
                .andExpect(result -> assertFalse(result.getResponse()
                        .getContentAsString().isBlank()));
    }

    private ResultActions performCreate(String username) throws Exception {
        return mockMvc.perform(post(BASIC_URL + "/{username}", username));
    }

    @Test
    @WithMockUser(username = "newget_testing")
    public void testGetByUsernameSuccess() throws Exception {
        String username = "newget_testing";
        createBagBeforeTesting(username);
        BagResponse expected = bagMapper.getBagResponseFromDomain(
                bagService.getByUsername(username));

        performGetBag(username)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expected),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testGetByUsernameNotFound() throws Exception {
        String username = "notfound";

        performGetBag(username)
                .andExpect(getNotFoundStatus())
                .andExpect(getNotFoundResult(username));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testGetByUsernameForbidden() throws Exception {
        String username = "newget_testing";

        performGetBag(username)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performGetBag(String username) throws Exception {
        return mockMvc.perform(get(BASIC_URL + "/{username}", username));
    }

    @Test
    @WithMockUser(username = "newadd_clothes_testing")
    public void testAddClothesToBagSuccess() throws Exception {
        String username = "newadd_clothes_testing";
        Long clothesId = 24L;
        createBagBeforeTesting(username);
        Bag bag = bagService.getByUsername(username);
        assertTrue(bag.getClothesIds().isEmpty());

        performPutClothes(username, clothesId)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("You added new clothe to your bag!",
                        result.getResponse().getContentAsString()));

        Bag bagAfterPutClothes = bagService.getByUsername(username);
        assertFalse(bagAfterPutClothes.getClothesIds().isEmpty());
        assertTrue(bagAfterPutClothes.getClothesIds().contains(clothesId));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testAddClothesToBagNotFound() throws Exception {
        String username = "notfound";
        Long clothesId = 22L;

        performPutClothes(username, clothesId)
                .andExpect(getNotFoundStatus())
                .andExpect(getNotFoundResult(username));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testAddClothesToBagForbidden() throws Exception {
        String username = "newadd_clothes_testing";
        Long clothesId = 22L;

        performPutClothes(username, clothesId)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performPutClothes(String username, Long clothesId) throws Exception {
        return mockMvc.perform(put(BASIC_URL
                + "/{username}/clothes/{clothes-id}", username, clothesId));
    }

    @Test
    @WithMockUser(username = "newdelete_clothes_testing")
    public void testDeleteClothesFromBag() throws Exception {
        String username = "newdelete_clothes_testing";
        Long clothesId = 6L;
        createBagBeforeTesting(username);
        bagService.putClothesToBag(username, clothesId);
        Bag bag = bagService.getByUsername(username);
        assertTrue(bag.getClothesIds().contains(clothesId));

        performDeleteClothes(username, clothesId)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("You deleted clothe from your bag!",
                        result.getResponse().getContentAsString()));

        Bag bagAfterDeleting = bagService.getByUsername(username);
        assertTrue(bagAfterDeleting.getClothesIds().isEmpty());
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testDeleteClothesNotFound() throws Exception {
        String username = "notfound";
        Long clothesId = 6L;

        performDeleteClothes(username, clothesId)
                .andExpect(getNotFoundStatus())
                .andExpect(getNotFoundResult(username));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testDeleteClothesForbidden() throws Exception {
        String username = "newdelete_clothes_testing";
        Long clothesId = 6L;

        performDeleteClothes(username, clothesId)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performDeleteClothes(String username, Long clothesId) throws Exception {
        return mockMvc.perform(delete(BASIC_URL
                + "/{username}/clothes/{clothes-id}", username, clothesId));
    }

    @Test
    @WithMockUser(username = "newget_total_price")
    public void testGetTotalPriceSuccess() throws Exception {
        String username = "newget_total_price";
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(1234);
        createBagBeforeTesting(username);
        Bag bag = bagService.getByUsername(username);
        bag.setTotalPrice(expectedTotalPrice);
        bagRepository.save(bag);

        performGetTotalPrice(username)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(asJsonString(expectedTotalPrice),
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testGetTotalPriceNotFound() throws Exception {
        String username = "notfound";

        performGetTotalPrice(username)
                .andExpect(getNotFoundStatus())
                .andExpect(getNotFoundResult(username));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testGetTotalPriceForbidden() throws Exception {
        String username = "newget_total_price";

        performGetTotalPrice(username)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performGetTotalPrice(String username) throws Exception {
        return mockMvc.perform(get(BASIC_URL + "/{username}/total-price", username));
    }

    @Test
    @WithMockUser(username = "reset_bag")
    public void testResetBagSuccess() throws Exception {
        String username = "reset_bag";
        BigDecimal totalPrice = BigDecimal.valueOf(1234);
        createBagBeforeTesting(username);
        bagService.putClothesToBag(username, 12L);
        bagService.putClothesToBag(username, 23L);
        bagService.putClothesToBag(username, 31L);
        Bag bag = bagService.getByUsername(username);
        bag.setTotalPrice(totalPrice);
        bagRepository.save(bag);
        assertFalse(bag.getClothesIds().isEmpty());
        assertNotEquals(BigDecimal.ZERO, bag.getTotalPrice());

        performResetBag(username)
                .andExpect(status().isOk());

        Bag bagAfterReset = bagService.getByUsername(username);
        assertTrue(bagAfterReset.getClothesIds().isEmpty());
        assertEquals(BigDecimal.ZERO, bagAfterReset.getTotalPrice());
    }

    private void createBagBeforeTesting(String username) {
        bagService.create(username);
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testResetBagNotFound() throws Exception {
        String username = "notfound";

        performResetBag(username)
                .andExpect(getNotFoundStatus())
                .andExpect(getNotFoundResult(username));
    }

    private ResultMatcher getNotFoundStatus() {
        return status().isNotFound();
    }

    private ResultMatcher getNotFoundResult(String username) {
        return result -> assertTrue(result.getResponse().getContentAsString()
                .contains("\"status\":\"NOT_FOUND\"," +
                        "\"message\":\"Bag not found for username: " + username));
    }

    @Test
    @WithMockUser(username = "notfound")
    public void testResetBagForbidden() throws Exception {
        String username = "reset_bag";

        performResetBag(username)
                .andExpect(getForbiddenStatus())
                .andExpect(getForbiddenResult());
    }

    private ResultActions performResetBag(String username) throws Exception {
        return mockMvc.perform(post(BASIC_URL + "/{username}/reset-bag", username));
    }

    private ResultMatcher getForbiddenStatus() {
        return status().isForbidden();
    }

    private ResultMatcher getForbiddenResult() {
        return result -> assertTrue(result.getResponse().getContentAsString()
                .contains("\"status\":\"FORBIDDEN\",\"message\":\"Access Denied\""));
    }
}
