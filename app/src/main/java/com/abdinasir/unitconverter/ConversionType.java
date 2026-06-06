package com.abdinasir.unitconverter;

public enum ConversionType {
    CM_TO_M("Centimeters to Meters (cm → m)"),
    M_TO_CM("Meters to Centimeters (m → cm)"),
    G_TO_KG("Grams to Kilograms (g → kg)"),
    KG_TO_G("Kilograms to Grams (kg → g)"),
    KM_TO_M("Kilometers to Meters (km → m)"),
    M_TO_KM("Meters to Kilometers (m → km)"),
    C_TO_F("Celsius to Fahrenheit (°C → °F)"),
    F_TO_C("Fahrenheit to Celsius (°F → °C)");

    private final String label;

    ConversionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
