package no.itera.lego;

public class StandardStateReceiver implements StateReceiver {

    public void avoidEdge() {
        System.out.println("Avoid edge");
    }

    public void gotoTarget() {
        System.out.println("Goto target");
    }

    public void onTarget() {
        System.out.println("On target");
    }
}
