package com.ubs.api.controller;

import com.ubs.api.domain.dto.CalculateSalesDtoRequest;
import com.ubs.api.domain.dto.CalculateSalesDtoResponse;
import com.ubs.api.service.CalculateSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/calculateSales")
@RequiredArgsConstructor
public class CalculateSalesController {

    private final CalculateSalesService calculateSalesService;

    @PostMapping
    public ResponseEntity<List<CalculateSalesDtoResponse>> calculateSales(@Validated @RequestBody CalculateSalesDtoRequest request) {
        final List<CalculateSalesDtoResponse> response = calculateSalesService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
