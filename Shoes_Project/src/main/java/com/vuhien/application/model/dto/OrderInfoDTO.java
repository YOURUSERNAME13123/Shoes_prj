package com.vuhien.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    private long id;

    private long totalPrice;

    private int sizeVn;

    private double sizeUs;

    private double sizeCm;

    private String productName;

    private String productImg;

    public OrderInfoDTO(long id, long totalPrice, int sizeVn, String productName, String productImg) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.sizeVn = sizeVn;
        this.productName = productName;
        this.productImg = productImg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getSizeVn() {
        return sizeVn;
    }

    public void setSizeVn(int sizeVn) {
        this.sizeVn = sizeVn;
    }

    public double getSizeUs() {
        return sizeUs;
    }

    public void setSizeUs(double sizeUs) {
        this.sizeUs = sizeUs;
    }

    public double getSizeCm() {
        return sizeCm;
    }

    public void setSizeCm(double sizeCm) {
        this.sizeCm = sizeCm;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}