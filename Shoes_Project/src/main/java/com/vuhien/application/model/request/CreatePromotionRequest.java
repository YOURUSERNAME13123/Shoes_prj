package com.vuhien.application.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePromotionRequest {
    @NotBlank(message = "Mã code rỗng")
    @Pattern(regexp="^[0-9A-Z-]+$", message = "Mã code không đúng định dạng")
    private String code;

    @NotBlank(message = "Tên rỗng")
    @Size(min = 1, max = 300, message = "Độ dài tên từ 1 - 300 kí tự")
    private String name;

    @Min(1)
    @Max(2)
    @JsonProperty("discount_type")
    private int discountType;

    @JsonProperty("discount_value")
    private long discountValue;

    @JsonProperty("max_value")
    private long maxValue;

    @JsonProperty("is_public")
    private boolean isPublic;

    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonProperty("expired_date")
    private Timestamp expiredDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }
}
