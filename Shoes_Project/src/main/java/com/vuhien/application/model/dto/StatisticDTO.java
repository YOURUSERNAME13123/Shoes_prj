package com.vuhien.application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatisticDTO {
    private long sales;
    private long profit;
    private int quantity;

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String createdAt;

    public StatisticDTO(long sales, long profit, int quantity,String createdAt){
        this.sales = sales;
        this.profit = profit;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }
}
