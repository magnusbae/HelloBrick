package no.itera.lego.color;

import static no.itera.lego.color.Color.valueOf;
import static no.itera.lego.util.ColorCalibrationPropertiesReader.readColorCalibrationProperties;

import java.util.Arrays;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorSensor {

    public static final int RGB_FLOAT_TO_INT_RATIO = 1000;
    private final EV3ColorSensor ev3ColorSensor;
    private final SensorMode rgbMode;
    private final int sampleSize;
    private static final float[] COLOR_CHANNEL_CALIBRATION_VALUES;

    static {
        float[] values = readColorCalibrationProperties();
        LCD.drawString(Arrays.toString(values), 0, 0);
        COLOR_CHANNEL_CALIBRATION_VALUES = values;
    }

    public ColorSensor(Port port) {
        ev3ColorSensor = new EV3ColorSensor(port);
        rgbMode = ev3ColorSensor.getRGBMode();
        sampleSize = rgbMode.sampleSize();
    }

    public Color readColor() {
        int[] rgb = readSensorRgb();

        return valueOf(rgb[0], rgb[1], rgb[2]);
    }

    public int[] readSensorRgb() {
        float[] sample = new float[sampleSize];
        rgbMode.fetchSample(sample, 0);
        return calibrateChannels(normalizeRgb(sample));
    }

    private int[] calibrateChannels(int[] rgb) {
        int[] calibrated = new int[rgb.length];

        for (int i = 0; i < rgb.length; i++) {
            calibrated[i] = (int) (rgb[i] * COLOR_CHANNEL_CALIBRATION_VALUES[i]);
        }
        return calibrated;
    }

    private int[] normalizeRgb(float[] sample) {
        int[] intSample = new int[sample.length];

        for (int i = 0; i < sample.length; i++) {
            intSample[i] = (int) (sample[i] * RGB_FLOAT_TO_INT_RATIO);
        }
        return intSample;
    }
}
