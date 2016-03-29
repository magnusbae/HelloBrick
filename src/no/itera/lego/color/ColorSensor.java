package no.itera.lego.color;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

import static no.itera.lego.color.Color.*;

public class ColorSensor {

    public static final int RGB_FLOAT_TO_INT_RATIO = 1000;
    private final EV3ColorSensor ev3ColorSensor;
    private final SensorMode rgbMode;
    private final int sampleSize;

    public ColorSensor(Port port) {
        ev3ColorSensor = new EV3ColorSensor(port);
        rgbMode = ev3ColorSensor.getRGBMode();
        sampleSize = rgbMode.sampleSize();
    }

    public Color readColor(){
        int[] rgb = readSensorRgb();

        return valueOf(rgb[0], rgb[1], rgb[2]); //TODO //FIXME
    }

    public int[] readSensorRgb() {
        float[] sample = new float[sampleSize];
        rgbMode.fetchSample(sample, 0);
        return normalizeRgb(sample);
    }

    private int[] normalizeRgb(float[] sample) {
        int[] intSample = new int[sample.length];

        for (int i = 0; i < sample.length; i++) {
            intSample[i] = (int) (sample[i] * RGB_FLOAT_TO_INT_RATIO);
        }
        return intSample;
    }
}
