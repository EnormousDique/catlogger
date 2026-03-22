package ru.muwa.catlog.model;

public enum FoodType {
    DRY(350),
    WET(90),
    DREAMIES(420);

    private final int kcalPer100Gram; 

    FoodType(int kcalPer100Gram) {
        this.kcalPer100Gram = kcalPer100Gram;
    }

    public double getKcalPer100Gram() {
        return kcalPer100Gram;
    }

}
