package no.itera.lego.util;

import no.itera.lego.model.TwoAxisInputModel;

public class LastState {
    private int lastByte = 128; //robot doing nothing;
    public TwoAxisInputModel lastInputModel = new TwoAxisInputModel(true, false, 0, 0);

    public int getLastByte() {
        return lastByte;
    }

    public void setLastByte(int lastByte) {
        this.lastByte = lastByte;
    }

    public TwoAxisInputModel getLastInputModel() {
        return lastInputModel;
    }

    public void setLastInputModel(TwoAxisInputModel lastInputModel) {
        this.lastInputModel = lastInputModel;
    }
}
