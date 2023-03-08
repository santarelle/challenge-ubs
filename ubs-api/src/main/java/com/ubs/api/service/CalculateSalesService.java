package com.ubs.api.service;

import com.ubs.api.domain.dto.CalculateSalesDtoRequest;
import com.ubs.api.domain.dto.CalculateSalesDtoResponse;

import java.util.List;

public interface CalculateSalesService {
    List<CalculateSalesDtoResponse> calculate(CalculateSalesDtoRequest request);
}
