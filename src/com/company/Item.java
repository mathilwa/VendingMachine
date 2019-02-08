package com.company;

public enum Item {
    CHOCOLATE(0.65f), BEER(1.00f), AVOCADO(1.50f);

    private final float price;

    Item(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
