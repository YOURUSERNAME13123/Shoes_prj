package com.vuhien.application.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateDetailOrder {

    @NotBlank(message = "Sản phẩm trống")
    @JsonProperty("product_id")
    private String productId;

    @Min(value = 35, message = "Kích thước tối thiểu là 35")
    @Max(value = 42, message = "Kích thước tối đa là 42")
    private int size;

    @Size(max = 50, message = "Độ dài mã giảm giá tối đa là 50 ký tự")
    @JsonProperty("coupon_code")
    private String couponCode;

    @PositiveOrZero(message = "Giá trị tổng cộng không thể âm")
    @JsonProperty("total_price")
    private long totalPrice;

    @PositiveOrZero(message = "Giá sản phẩm không thể âm")
    @JsonProperty("product_price")
    private long productPrice;

}
