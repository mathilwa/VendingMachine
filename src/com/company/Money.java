package com.company;

public enum Money {
    $(1.00f), QUARTER(0.25f), DIME(0.10f), NICKEL(0.05f);

    private final float value;

    Money(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
