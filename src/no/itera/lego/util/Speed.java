package no.itera.lego.util;

public enum Speed {

    SLOW(300),
    MEDIUM(600),
    FAST(900),
    FASTEST(1200);

    private final int speed;

    Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

}
