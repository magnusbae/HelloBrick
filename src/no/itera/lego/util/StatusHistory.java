package no.itera.lego.util;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import no.itera.lego.message.Status;
import no.itera.lego.websocket.WebSocketListener;

public class StatusHistory {

    private final Set<WebSocketListener> listeners;
    private final Stack<Status> statusHistory;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public StatusHistory() {
        statusHistory = new Stack<>();
        listeners = new HashSet<>();
    }

    public void addListener(WebSocketListener listener) {
        listeners.add(listener);
    }

    public void addNewStatus(Status lastStatus) {
        statusHistory.push(lastStatus);

        notifyStatusListeners();
    }

    private void notifyStatusListeners() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (statusHistory.size() == 1) {
                    for (WebSocketListener listener : listeners) {
                        Status initial = statusHistory.peek();
                        System.out.println(String.format("Status: initial=%s", initial));
                        listener.initialStatusFired(initial);
                    }
                } else if (statusHistory.size() >= 2) {
                    for (WebSocketListener listener : listeners) {
                        Status oldStatus = statusHistory.get(statusHistory.size() - 2);
                        Status newStatus = statusHistory.peek();
                        System.out.println(String.format("Status: old=%s, new=%s", oldStatus, newStatus));
                        listener.newStatusFired(oldStatus, newStatus);
                    }
                }
            }
        });
    }

    public void clearListeners() {
        listeners.clear();
    }

}
