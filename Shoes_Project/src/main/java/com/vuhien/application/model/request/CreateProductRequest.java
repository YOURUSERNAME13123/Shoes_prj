package com.vuhien.application.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateProductRequest {
    private String id;

    @NotBlank(message = "Tên sản phẩm trống!")
    @Size(max = 300, message = "Tên sản phẩm có độ dài tối đa 300 ký tự!")
    private String name;

    @NotBlank(message = "Mô tả sản phẩm trống!")
    private String description;

    @NotNull(message = "Nhãn hiệu trống!")
    @JsonProperty("brand_id")
    private Long brandId;

    @NotNull(message = "Danh mục trống!")
    @JsonProperty("category_ids")
    private ArrayList<Integer> categoryIds;

    @Min(1000)
    @Max(1000000000)
    @NotNull(message = "Giá sản phẩm trống!")
    private Long price;

    @Min(1000)
    @Max(1000000000)
    @NotNull(message = "Giá bán sản phẩm trống!")
    private Long salePrice;

    @NotNull(message = "Danh sách ảnh trống!")
    @JsonProperty("product_images")
    private ArrayList<String> images;

    @JsonProperty("feed_back_images")
    private ArrayList<String> feedBackImages;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public ArrayList<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(ArrayList<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getFeedBackImages() {
        return feedBackImages;
    }

    public void setFeedBackImages(ArrayList<String> feedBackImages) {
        this.feedBackImages = feedBackImages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
