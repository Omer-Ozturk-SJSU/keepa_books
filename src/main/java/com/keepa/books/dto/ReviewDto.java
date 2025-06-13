package com.keepa.books.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Double rating;
    private Integer totalReviews;
    private Integer totalReviewCount;
    private Integer lastReviewUpdate;
    private Integer lastRatingUpdate;
    private Integer lastReviewCountUpdate;
    private Integer lastReviewCount;
    private Integer lastRating;
    private Integer lastTotalReviews;
}