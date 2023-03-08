package com.ubs.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.api.UbsApiApplication;
import com.ubs.api.domain.dto.CalculateSalesDetail;
import com.ubs.api.domain.dto.CalculateSalesDtoRequest;
import com.ubs.api.domain.dto.CalculateSalesDtoResponse;
import com.ubs.api.domain.dto.CalculateSalesSummary;
import com.ubs.api.domain.entity.Stock;
import com.ubs.api.fixture.StockFixture;
import com.ubs.api.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebAppConfiguration
@SpringBootTest(classes = {UbsApiApplication.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@WebMvcTest({CalculateSalesController.class})
class CalculateSalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockRepository stockRepository;

    @Test
    void calculateSalesWithTwoStores() throws Exception {
        CalculateSalesDtoRequest givenRequest = new CalculateSalesDtoRequest();
        givenRequest.setProduct("EMMS");
        givenRequest.setStoreQuantity(2);

        final List<Stock> mockStock = new ArrayList<>();
        mockStock.add(StockFixture.product("EMMS", 74, new BigDecimal("3.75")));
        mockStock.add(StockFixture.product("EMMS", 36, new BigDecimal("5.39")));
        mockStock.add(StockFixture.product("EMMS", 99, new BigDecimal("5.80")));
        mockStock.add(StockFixture.product("EMMS", 61, new BigDecimal("7.45")));

        when(stockRepository.findByproductName("EMMS")).thenReturn(mockStock);

        final MvcResult result = mockMvc.perform(post("/v1/calculateSales")
                        .content(objectMapper.writeValueAsString(givenRequest))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andReturn();

        List<CalculateSalesDtoResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(CalculateSalesDtoResponse.builder()
                .store("Loja 1")
                .details(Arrays.asList(
                        CalculateSalesDetail.builder().quantity(37).price(new BigDecimal("3.75")).volume(new BigDecimal("138.75")).build(),
                        CalculateSalesDetail.builder().quantity(18).price(new BigDecimal("5.39")).volume(new BigDecimal("97.02")).build(),
                        CalculateSalesDetail.builder().quantity(50).price(new BigDecimal("5.80")).volume(new BigDecimal("290.00")).build(),
                        CalculateSalesDetail.builder().quantity(30).price(new BigDecimal("7.45")).volume(new BigDecimal("223.50")).build()
                ))
                .summary(CalculateSalesSummary.builder().quantity(135).financial(new BigDecimal("749.27")).averagePrice(new BigDecimal("5.5501")).build())
                .build());

        expectedResponse.add(CalculateSalesDtoResponse.builder()
                .store("Loja 2")
                .details(Arrays.asList(
                        CalculateSalesDetail.builder().quantity(37).price(new BigDecimal("3.75")).volume(new BigDecimal("138.75")).build(),
                        CalculateSalesDetail.builder().quantity(18).price(new BigDecimal("5.39")).volume(new BigDecimal("97.02")).build(),
                        CalculateSalesDetail.builder().quantity(49).price(new BigDecimal("5.80")).volume(new BigDecimal("284.20")).build(),
                        CalculateSalesDetail.builder().quantity(31).price(new BigDecimal("7.45")).volume(new BigDecimal("230.95")).build()
                ))
                .summary(CalculateSalesSummary.builder().quantity(135).financial(new BigDecimal("750.92")).averagePrice(new BigDecimal("5.5624")).build())
                .build());

        assertEquals(200, result.getResponse().getStatus());
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    void calculateSalesWithThreeStores() throws Exception {
        CalculateSalesDtoRequest givenRequest = new CalculateSalesDtoRequest();
        givenRequest.setProduct("EMMS");
        givenRequest.setStoreQuantity(3);

        final List<Stock> mockStock = new ArrayList<>();
        mockStock.add(StockFixture.product("EMMS", 74, new BigDecimal("3.75")));
        mockStock.add(StockFixture.product("EMMS", 36, new BigDecimal("5.39")));
        mockStock.add(StockFixture.product("EMMS", 99, new BigDecimal("5.80")));
        mockStock.add(StockFixture.product("EMMS", 61, new BigDecimal("7.45")));

        when(stockRepository.findByproductName("EMMS")).thenReturn(mockStock);

        final MvcResult result = mockMvc.perform(post("/v1/calculateSales")
                        .content(objectMapper.writeValueAsString(givenRequest))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andReturn();

        List<CalculateSalesDtoResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(CalculateSalesDtoResponse.builder()
                .store("Loja 1")
                .details(Arrays.asList(
                        CalculateSalesDetail.builder().quantity(12).price(new BigDecimal("5.39")).volume(new BigDecimal("64.68")).build(),
                        CalculateSalesDetail.builder().quantity(33).price(new BigDecimal("5.80")).volume(new BigDecimal("191.40")).build(),
                        CalculateSalesDetail.builder().quantity(25).price(new BigDecimal("3.75")).volume(new BigDecimal("93.75")).build(),
                        CalculateSalesDetail.builder().quantity(20).price(new BigDecimal("7.45")).volume(new BigDecimal("149.00")).build()
                ))
                .summary(CalculateSalesSummary.builder().quantity(90).financial(new BigDecimal("498.83")).averagePrice(new BigDecimal("5.5426")).build())
                .build());

        expectedResponse.add(CalculateSalesDtoResponse.builder()
                .store("Loja 2")
                .details(Arrays.asList(
                        CalculateSalesDetail.builder().quantity(12).price(new BigDecimal("5.39")).volume(new BigDecimal("64.68")).build(),
                        CalculateSalesDetail.builder().quantity(33).price(new BigDecimal("5.80")).volume(new BigDecimal("191.40")).build(),
                        CalculateSalesDetail.builder().quantity(25).price(new BigDecimal("3.75")).volume(new BigDecimal("93.75")).build(),
                        CalculateSalesDetail.builder().quantity(20).price(new BigDecimal("7.45")).volume(new BigDecimal("149.00")).build()
                ))
                .summary(CalculateSalesSummary.builder().quantity(90).financial(new BigDecimal("498.83")).averagePrice(new BigDecimal("5.5426")).build())
                .build());

        expectedResponse.add(CalculateSalesDtoResponse.builder()
                .store("Loja 3")
                .details(Arrays.asList(
                        CalculateSalesDetail.builder().quantity(12).price(new BigDecimal("5.39")).volume(new BigDecimal("64.68")).build(),
                        CalculateSalesDetail.builder().quantity(33).price(new BigDecimal("5.80")).volume(new BigDecimal("191.40")).build(),
                        CalculateSalesDetail.builder().quantity(24).price(new BigDecimal("3.75")).volume(new BigDecimal("90.00")).build(),
                        CalculateSalesDetail.builder().quantity(21).price(new BigDecimal("7.45")).volume(new BigDecimal("156.45")).build()
                ))
                .summary(CalculateSalesSummary.builder().quantity(90).financial(new BigDecimal("502.53")).averagePrice(new BigDecimal("5.5837")).build())
                .build());

        assertEquals(200, result.getResponse().getStatus());
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expectedResponse));
    }
}