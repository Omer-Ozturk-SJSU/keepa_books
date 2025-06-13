package com.keepa.books.dto;

import lombok.Data;
import java.util.List;

@Data
public class RentalDto {
    private Integer current;
    private List<Integer> history;
    private Integer min;
    private Integer max;
    private Integer avg;
}