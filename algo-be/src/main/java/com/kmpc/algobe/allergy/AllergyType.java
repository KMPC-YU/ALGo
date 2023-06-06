package com.kmpc.algobe.allergy;

import lombok.Getter;

@Getter
public enum AllergyType {
    BEEF(1),
    BUCKWHEAT(1 << 1),
    CHICKEN(1 << 2),
    CLAMS(1 << 3),
    CRAB(1 << 4),
    EGGS(1 << 5),
    FISH(1 << 6),
    FRUIT(1 << 7),
    GARLIC(1 << 8),
    MILK(1 << 9),
    NUTS(1 << 10),
    PORK(1 << 11),
    SESAME(1 << 12),
    SHRIMP(1 << 13),
    SOYBEAN(1 << 14),
    SQUID(1 << 15),
    SULPHITE(1 << 16),
    TOMATO(1 << 17),
    VEGETABLE(1 << 18),
    WHEAT(1 << 19);

    private int allergyBit;

    AllergyType(int i) {
        this.allergyBit = i;
    }
}
