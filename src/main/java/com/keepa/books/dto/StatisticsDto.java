package com.keepa.books.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class StatisticsDto {
    @JsonProperty("current")
    private JsonNode current;

    @JsonProperty("avg")
    private JsonNode avg;

    @JsonProperty("avg30")
    private JsonNode avg30;

    @JsonProperty("avg90")
    private JsonNode avg90;

    @JsonProperty("avg180")
    private JsonNode avg180;

    @JsonProperty("min")
    private JsonNode min;

    @JsonProperty("max")
    private JsonNode max;

    @JsonProperty("outOfStockPercentage")
    private JsonNode outOfStockPercentage;

    @JsonProperty("outOfStockPercentage30")
    private JsonNode outOfStockPercentage30;

    @JsonProperty("outOfStockPercentage90")
    private JsonNode outOfStockPercentage90;

    @JsonProperty("outOfStockPercentage180")
    private JsonNode outOfStockPercentage180;

    @JsonProperty("total")
    private JsonNode total;

    @JsonProperty("count")
    private JsonNode count;

    @JsonProperty("count30")
    private JsonNode count30;

    @JsonProperty("count90")
    private JsonNode count90;

    @JsonProperty("count180")
    private JsonNode count180;

    @JsonProperty("priceHistory")
    private Map<String, List<Integer>> priceHistory;

    @JsonProperty("salesRankReference")
    private Long salesRankReference;

    @JsonProperty("salesRankReferenceHistory")
    private List<Long> salesRankReferenceHistory;

    @JsonProperty("salesRanks")
    private Map<String, List<Integer>> salesRanks;
}