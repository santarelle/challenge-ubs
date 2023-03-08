package com.ubs.api.service.impl;

import com.ubs.api.domain.dto.CalculateSalesDetail;
import com.ubs.api.domain.dto.CalculateSalesDtoRequest;
import com.ubs.api.domain.dto.CalculateSalesDtoResponse;
import com.ubs.api.domain.dto.CalculateSalesSummary;
import com.ubs.api.domain.entity.Stock;
import com.ubs.api.repository.StockRepository;
import com.ubs.api.service.CalculateSalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateSalesServiceImpl implements CalculateSalesService {

    private final StockRepository stockRepository;

    @Override
    public List<CalculateSalesDtoResponse> calculate(final CalculateSalesDtoRequest request) {
        final List<CalculateSalesDtoResponse> salesResult = new ArrayList<>();

        final List<Stock> stockDbList = stockRepository.findByproductName(request.getProduct());
        if (!stockDbList.isEmpty()) {

            final BigDecimal sumOfQuantity = new BigDecimal(stockDbList.stream().mapToInt(Stock::getQuantity).sum());
            final BigDecimal sumOfVolume = stockDbList.stream().map(Stock::getVolume).reduce(BigDecimal.ZERO, BigDecimal::add);
            final BigDecimal avgPrice = sumOfVolume.divide(sumOfQuantity, RoundingMode.HALF_EVEN);
            final Integer qtyStores = request.getStoreQuantity();
            log.info("sumOfQuantity={} sumOfVolume={} avgPrice={}", sumOfQuantity, sumOfVolume, avgPrice);

            prepareStores(qtyStores, salesResult);
            final List<Stock> stockDividedEqually = stockDbList.stream().filter(s -> s.getQuantity() % qtyStores == 0).collect(toList());
            distributeProductEqually(qtyStores, salesResult, stockDividedEqually);

            final List<Stock> stockDistributionPending = stockDbList.stream().filter(s -> s.getQuantity() % qtyStores != 0).collect(toList());
            distributeProductPending(sumOfQuantity, qtyStores, salesResult, stockDistributionPending);

            salesResult.forEach(sales -> sales.setSummary(calculateSummary(sales.getDetails())));
        }

        return salesResult;
    }

    private void prepareStores(Integer qtyStores, List<CalculateSalesDtoResponse> salesResult) {
        salesResult.addAll(IntStream.range(0, qtyStores).boxed()
                .map(storeId -> CalculateSalesDtoResponse.builder()
                        .store("Loja " + (storeId + 1))
                        .details(new ArrayList<>())
                        .build())
                .collect(toList()));
    }

    private void distributeProductEqually(Integer qtyStores, List<CalculateSalesDtoResponse> salesResult, List<Stock> stockDividedEqually) {
        stockDividedEqually.forEach(stockDB ->
                salesResult.forEach(sales -> {
                    int qtyProduct = stockDB.getQuantity() / qtyStores;
                    sales.getDetails().add(buildSalesDetail(stockDB, qtyProduct));
                })
        );
    }

    private void distributeProductPending(BigDecimal sumOfQuantity, Integer qtyStores, List<CalculateSalesDtoResponse> salesResult, List<Stock> stockDistributionPending) {
        stockDistributionPending.forEach(stockDB -> {
            final AtomicInteger restProduct = new AtomicInteger(stockDB.getQuantity() % qtyStores);
            final int qtyProduct = stockDB.getQuantity() / qtyStores;
            salesResult.forEach(sale -> {
                final List<CalculateSalesDetail> actualDetails = sale.getDetails();
                final List<CalculateSalesDetail> tempSalesDetail = new ArrayList<>(actualDetails);
                final CalculateSalesDetail tempSaleItem = buildSalesDetail(stockDB, qtyProduct + (restProduct.get() > 0 ? 1 : 0));
                tempSalesDetail.add(tempSaleItem);
                final CalculateSalesSummary tempSummary = calculateSummary(tempSalesDetail);

                final Integer limitQuantity = sumOfQuantity.divide(BigDecimal.valueOf(qtyStores), RoundingMode.UP).intValue();
                final int balanceQuantity = limitQuantity - tempSummary.getQuantity();
                if (balanceQuantity >= 0) {
                    actualDetails.add(tempSaleItem);
                    restProduct.set(restProduct.get() - 1);
                } else {
                    actualDetails.add(buildSalesDetail(stockDB, qtyProduct));
                }
            });
        });
    }

    private CalculateSalesDetail buildSalesDetail(Stock stockDB, int qtyProduct) {
        return CalculateSalesDetail.builder()
                .quantity(qtyProduct)
                .price(stockDB.getPrice())
                .volume(new BigDecimal(qtyProduct).multiply(stockDB.getPrice()))
                .build();
    }

    private CalculateSalesSummary calculateSummary(List<CalculateSalesDetail> details) {
        final int summaryQuantity = details.stream().mapToInt(CalculateSalesDetail::getQuantity).sum();
        final BigDecimal summaryFinancial = details.stream().map(CalculateSalesDetail::getVolume).reduce(BigDecimal.ZERO, BigDecimal::add);

        return CalculateSalesSummary.builder()
                .quantity(summaryQuantity)
                .financial(summaryFinancial)
                .averagePrice(summaryFinancial.divide(BigDecimal.valueOf(summaryQuantity), 4, RoundingMode.HALF_EVEN))
                .build();
    }
}
