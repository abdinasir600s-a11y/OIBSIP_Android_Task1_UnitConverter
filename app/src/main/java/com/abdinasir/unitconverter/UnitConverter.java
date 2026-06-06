package com.abdinasir.unitconverter;

public class UnitConverter {

    public static double convert(double value, ConversionType type) {
        switch (type) {
            case CM_TO_M:
                return value / 100.0;
            case M_TO_CM:
                return value * 100.0;
            case G_TO_KG:
                return value / 1000.0;
            case KG_TO_G:
                return value * 1000.0;
            case KM_TO_M:
                return value * 1000.0;
            case M_TO_KM:
                return value / 1000.0;
            case C_TO_F:
                return (value * 9.0 / 5.0) + 32.0;
            case F_TO_C:
                return (value - 32.0) * 5.0 / 9.0;
            default:
                throw new IllegalArgumentException("Unknown conversion type");
        }
    }

    public static String getInputUnit(ConversionType type) {
        switch (type) {
            case CM_TO_M: return "cm";
            case M_TO_CM: return "m";
            case G_TO_KG: return "g";
            case KG_TO_G: return "kg";
            case KM_TO_M: return "km";
            case M_TO_KM: return "m";
            case C_TO_F: return "°C";
            case F_TO_C: return "°F";
            default: return "";
        }
    }

    public static String getOutputUnit(ConversionType type) {
        switch (type) {
            case CM_TO_M: return "m";
            case M_TO_CM: return "cm";
            case G_TO_KG: return "kg";
            case KG_TO_G: return "g";
            case KM_TO_M: return "m";
            case M_TO_KM: return "km";
            case C_TO_F: return "°F";
            case F_TO_C: return "°C";
            default: return "";
        }
    }
}
