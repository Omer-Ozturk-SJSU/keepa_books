package com.keepa.books.dto;

import lombok.Data;

@Data
public class FbaFees {
    private Integer pickAndPack;
    private Integer storage;
    private Integer weightHandling;
    private Integer orderHandling;
    private Integer total;
}