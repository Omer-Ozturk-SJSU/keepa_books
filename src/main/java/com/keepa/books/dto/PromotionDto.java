package com.keepa.books.dto;

import lombok.Data;

@Data
public class PromotionDto {
    private Integer type;
    private Integer value;
    private Integer startDate;
    private Integer endDate;
    private String description;
    private Boolean isPrimeExclusive;
    private Boolean isLightningDeal;
    private Boolean isDealOfTheDay;
}