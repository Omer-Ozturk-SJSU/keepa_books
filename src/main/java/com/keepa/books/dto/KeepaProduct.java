package com.keepa.books.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class KeepaProduct {
    @JsonProperty("productType")
    private Integer productType;

    private String asin;

    @JsonProperty("domainId")
    private Integer domainId;

    private String title;

    @JsonProperty("trackingSince")
    private Integer trackingSince;

    @JsonProperty("listedSince")
    private Integer listedSince;

    @JsonProperty("lastUpdate")
    private Integer lastUpdate;

    @JsonProperty("lastRatingUpdate")
    private Integer lastRatingUpdate;

    @JsonProperty("lastPriceChange")
    private Integer lastPriceChange;

    @JsonProperty("lastEbayUpdate")
    private Integer lastEbayUpdate;

    @JsonProperty("lastStockUpdate")
    private Integer lastStockUpdate;

    private List<ImageDto> images;

    @JsonProperty("rootCategory")
    private Integer rootCategory;

    private List<Long> categories;

    @JsonProperty("categoryTree")
    private List<Object> categoryTree;

    @JsonProperty("parentAsin")
    private String parentAsin;

    @JsonProperty("parentAsinHistory")
    private List<String> parentAsinHistory;

    @JsonProperty("frequentlyBoughtTogether")
    private List<String> frequentlyBoughtTogether;

    @JsonProperty("eanList")
    private List<String> eanList;

    @JsonProperty("upcList")
    private List<String> upcList;

    @JsonProperty("gtinList")
    private List<String> gtinList;

    private String manufacturer;
    private String brand;

    @JsonProperty("brandStoreName")
    private String brandStoreName;

    @JsonProperty("brandStoreUrl")
    private String brandStoreUrl;

    @JsonProperty("brandStoreUrlName")
    private String brandStoreUrlName;

    @JsonProperty("websiteDisplayGroup")
    private String websiteDisplayGroup;

    @JsonProperty("websiteDisplayGroupName")
    private String websiteDisplayGroupName;

    @JsonProperty("salesRankDisplayGroup")
    private String salesRankDisplayGroup;

    @JsonProperty("productGroup")
    private String productGroup;

    private String type;

    @JsonProperty("partNumber")
    private String partNumber;

    private String binding;
    private String scent;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("activeIngredients")
    private String activeIngredients;

    @JsonProperty("specialIngredients")
    private String specialIngredients;

    @JsonProperty("itemForm")
    private String itemForm;

    @JsonProperty("itemTypeKeyword")
    private String itemTypeKeyword;

    @JsonProperty("recommendedUsesForProduct")
    private String recommendedUsesForProduct;

    private String pattern;

    @JsonProperty("specificUsesForProduct")
    private List<String> specificUsesForProduct;

    @JsonProperty("businessDiscount")
    private Integer businessDiscount;

    @JsonProperty("lastBusinessDiscountUpdate")
    private Integer lastBusinessDiscountUpdate;

    @JsonProperty("safetyWarning")
    private String safetyWarning;

    @JsonProperty("productBenefit")
    private String productBenefit;

    @JsonProperty("batteriesRequired")
    private Boolean batteriesRequired;

    @JsonProperty("batteriesIncluded")
    private Boolean batteriesIncluded;

    @JsonProperty("targetAudienceKeyword")
    private String targetAudienceKeyword;

    private String style;

    @JsonProperty("includedComponents")
    private String includedComponents;

    private String material;

    @JsonProperty("numberOfItems")
    private Integer numberOfItems;

    @JsonProperty("numberOfPages")
    private Integer numberOfPages;

    @JsonProperty("publicationDate")
    private Integer publicationDate;

    @JsonProperty("releaseDate")
    private Integer releaseDate;

    private List<List<String>> contributors;
    private List<List<String>> languages;
    private String model;
    private String color;
    private String size;
    private String edition;
    private String format;
    private List<String> features;
    private String description;

    @JsonProperty("hazardousMaterials")
    private List<Object> hazardousMaterials;

    private List<Object> videos;
    private List<Object> aPlus;

    @JsonProperty("packageHeight")
    private Integer packageHeight;

    @JsonProperty("packageLength")
    private Integer packageLength;

    @JsonProperty("packageWidth")
    private Integer packageWidth;

    @JsonProperty("packageWeight")
    private Integer packageWeight;

    @JsonProperty("packageQuantity")
    private Integer packageQuantity;

    @JsonProperty("itemHeight")
    private Integer itemHeight;

    @JsonProperty("itemLength")
    private Integer itemLength;

    @JsonProperty("itemWidth")
    private Integer itemWidth;

    @JsonProperty("itemWeight")
    private Integer itemWeight;

    @JsonProperty("availabilityAmazon")
    private Integer availabilityAmazon;

    @JsonProperty("availabilityAmazonDelay")
    private List<Integer> availabilityAmazonDelay;

    @JsonProperty("buyBoxEligibleOfferCounts")
    private List<Integer> buyBoxEligibleOfferCounts;

    @JsonProperty("competitivePriceThreshold")
    private Integer competitivePriceThreshold;

    @JsonProperty("suggestedLowerPrice")
    private Integer suggestedLowerPrice;

    @JsonProperty("ebayListingIds")
    private List<Long> ebayListingIds;

    @JsonProperty("isAdultProduct")
    private Boolean isAdultProduct;

    @JsonProperty("isHeatSensitive")
    private Boolean isHeatSensitive;

    @JsonProperty("isMerchOnDemand")
    private Boolean isMerchOnDemand;

    @JsonProperty("isHaul")
    private Boolean isHaul;

    private Boolean launchpad;

    @JsonProperty("audienceRating")
    private String audienceRating;

    private String ingredients;

    @JsonProperty("urlSlug")
    private String urlSlug;

    @JsonProperty("returnRate")
    private Integer returnRate;

    @JsonProperty("newPriceIsMAP")
    private Boolean newPriceIsMAP;

    @JsonProperty("isEligibleForTradeIn")
    private Boolean isEligibleForTradeIn;

    @JsonProperty("isEligibleForSuperSaverShipping")
    private Boolean isEligibleForSuperSaverShipping;

    @JsonProperty("fbaFees")
    private FbaFeesDto fbaFees;

    @JsonProperty("variableClosingFee")
    private Integer variableClosingFee;

    @JsonProperty("referralFeePercentage")
    private Double referralFeePercentage;

    private List<Object> variations;
    private List<Integer> coupon;

    @JsonProperty("couponHistory")
    private List<Integer> couponHistory;

    private List<PromotionDto> promotions;
    private List<FormatDto> formats;

    @JsonProperty("unitCount")
    private UnitCountDto unitCount;

    private StatisticsDto stats;

    @JsonProperty("salesRankReference")
    private Long salesRankReference;

    @JsonProperty("salesRankReferenceHistory")
    private List<Long> salesRankReferenceHistory;

    @JsonProperty("salesRanks")
    private Map<String, List<Integer>> salesRanks;

    @JsonProperty("lastSoldUpdate")
    private Integer lastSoldUpdate;

    @JsonProperty("monthlySold")
    private Integer monthlySold;

    @JsonProperty("monthlySoldHistory")
    private List<Integer> monthlySoldHistory;

    @JsonProperty("rentalDetails")
    private String rentalDetails;

    @JsonProperty("rentalSellerId")
    private String rentalSellerId;

    @JsonProperty("rentalPrices")
    private RentalDto rentalPrices;

    private ReviewDto reviews;
    private List<MarketplaceOfferDto> offers;

    @JsonProperty("liveOffersOrder")
    private List<Integer> liveOffersOrder;

    @JsonProperty("buyBoxSellerIdHistory")
    private List<String> buyBoxSellerIdHistory;

    @JsonProperty("buyBoxUsedHistory")
    private List<String> buyBoxUsedHistory;

    @JsonProperty("isRedirectASIN")
    private Boolean isRedirectASIN;

    @JsonProperty("isSNS")
    private Boolean isSNS;

    @JsonProperty("offersSuccessful")
    private Boolean offersSuccessful;

    private List<List<Integer>> csv;
}