package no.itera.lego.example;

import no.itera.lego.EV3Helper;

public class SampleSet {
    private float lastDistance;
    private String lastColor;
    private final EV3Helper ev3Helper;

    public SampleSet(EV3Helper ev3Helper) {
        this.ev3Helper = ev3Helper;
    }

    public void takeSamples(){
        setLastDistance(ev3Helper.getDistance());
        setLastColor(ev3Helper.getColorName());
    }

    public float getLastDistance() {
        return lastDistance;
    }

    public void setLastDistance(float lastDistance) {
        this.lastDistance = lastDistance;
    }

    public String getLastColor() {
        return lastColor;
    }

    public void setLastColor(String lastColor) {
        this.lastColor = lastColor;
    }
}
