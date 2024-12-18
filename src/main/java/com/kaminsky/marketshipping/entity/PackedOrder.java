package com.kaminsky.marketshipping.entity;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PackedOrder {
    private UUID id;

    @NotNull(message = "Стоимость не может быть null")
    private int price;

    private Status status;

    private boolean isPacked;

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    @Override
    public String toString() {
        return id + " " + price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}