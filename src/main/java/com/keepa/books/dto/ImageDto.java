package com.keepa.books.dto;

import lombok.Data;

@Data
public class ImageDto {
    private String imageUrl;
    private Integer imageId;
    private Integer imageType;
    private Integer width;
    private Integer height;
}