package com.nju.jdk15;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @description
 * @date 2024/6/12 0:23
 * @author: qyl
 */
public class RecordsExamples {
    class Merchant{}

    public List<Merchant> findTopMerchants(List<Merchant> merchants, int month) {
        // 局部记录
        record MerchantSales(Merchant merchant, double sales) {}

        return merchants.stream()
                .map(merchant -> new MerchantSales(merchant, computeSales(merchant, month)))
                .sorted((m1, m2) -> Double.compare(m2.sales(), m1.sales()))
                .map(MerchantSales::merchant)
                .collect(toList());
    }

    private double computeSales(Merchant merchant, int month) {
        return 1.0;
    }
}
