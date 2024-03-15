package com.vuhien.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
//@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",nullable = false,length = 300)
    private String name;
    @Column(name = "coupon_code",unique = true)
    private String couponCode;
    @Column(name = "discount_type")
    private int discountType;
    @Column(name = "discount_value")
    private long discountValue;
    @Column(name = "maximum_discount_value")
    private long maximumDiscountValue;
    @Column(name = "is_active",columnDefinition = "TINYINT(1)")
    private boolean isActive;
    @Column(name = "is_public",columnDefinition = "TINYINT(1)")
    private boolean isPublic;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "expired_at")
    private Timestamp expiredAt;

    public Promotion(Order.UsedPromotion promotion) {
        this.couponCode = promotion.getCouponCode();
        this.discountType = promotion.getDiscountType();
        this.discountValue = promotion.getDiscountValue();
        this.maximumDiscountValue = promotion.getMaximumDiscountValue();
    }

    public Promotion() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    public long getMaximumDiscountValue() {
        return maximumDiscountValue;
    }

    public void setMaximumDiscountValue(long maximumDiscountValue) {
        this.maximumDiscountValue = maximumDiscountValue;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }
}
