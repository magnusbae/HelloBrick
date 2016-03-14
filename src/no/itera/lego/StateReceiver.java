package no.itera.lego;

public interface StateReceiver {

    public void avoidEdge();

    public void gotoTarget();

    public void onTarget();
}
