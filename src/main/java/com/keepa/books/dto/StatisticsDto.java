package com.keepa.books.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Statistics {
    private Integer current;
    private Integer avg;
    private Integer avg30;
    private Integer avg90;
    private Integer avg180;
    private Integer min;
    private Integer max;
    private Integer outOfStockPercentage;
    private Integer outOfStockPercentage30;
    private Integer outOfStockPercentage90;
    private Integer outOfStockPercentage180;
    private Integer total;
    private Integer count;
    private Integer count30;
    private Integer count90;
    private Integer count180;
    private Map<String, List<Integer>> priceHistory;
}