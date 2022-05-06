package pl.sii.itconference.utils;

public class FloatUtils {

    public static float round(float value, int decimalPlaces) {
        return (float) (Math.round(value * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));
    }
}
