package no.itera.lego.color;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

import static no.itera.lego.color.Color.*;

public class ColorSensor {

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

        //TODO Determine color from RGB values

        return BLACK; //TODO //FIXME
    }

    public int[] readSensorRgb() {
        float[] sample = new float[sampleSize];
        rgbMode.fetchSample(sample, 0);
        return normalizeRgb(sample);
    }

    private int[] normalizeRgb(float[] sample) {
        int[] intSample = new int[sample.length];

        for (int i = 0; i < sample.length; i++) {
            intSample[i] = (int) (sample[i] * 100);
        }
        return intSample;
    }
}
