package no.itera.lego;

import no.itera.lego.message.Status;

public interface WebSocketListener {

    void initialStatusFired(Status initial);

    void newStatusFired(Status oldStatus, Status newStatus);

}
