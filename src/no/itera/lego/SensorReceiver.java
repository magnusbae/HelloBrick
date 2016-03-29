package no.itera.lego;

import no.itera.lego.color.Color;

public interface SensorReceiver {

    public void receiveColor(Color color);

    public void receiveDistance(float distance);
}
