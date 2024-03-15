package com.vuhien.application.model.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Setter
@Getter
public class ShortProductInfoDTO {
    private String id;

    private String name;

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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<Integer> getAvailableSizes() {
        return availableSizes;
    }

    public void setAvailableSizes(List<Integer> availableSizes) {
        this.availableSizes = availableSizes;
    }

    private long price;

    List<Integer> availableSizes;

    public ShortProductInfoDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ShortProductInfoDTO(String id, String name, long price, Object availableSizes) {
        this.id = id;
        this.name = name;
        this.price = price;
        if (availableSizes != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.availableSizes = mapper.readValue((String) availableSizes, new TypeReference<List<Integer>>(){});
            } catch (IOException e) {
                this.availableSizes = null;
            }
        }
    }
}