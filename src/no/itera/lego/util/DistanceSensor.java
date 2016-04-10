package no.itera.lego.util;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;

public class DistanceSensor {

    private final EV3IRSensor ev3IrSensor;
    private final SensorMode distanceMode;

    public DistanceSensor(Port port) {
        ev3IrSensor = new EV3IRSensor(port);
        distanceMode = ev3IrSensor.getDistanceMode();
    }

    public float readDistance() {
        float[] sample = new float[distanceMode.sampleSize()];
        distanceMode.fetchSample(sample, 0);
        return sample[0];
    }
}
