package no.itera.lego.util;

import no.itera.lego.WebSocketListener;
import no.itera.lego.message.Status;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                        listener.initialStatusFired(statusHistory.peek());
                    }
                } else if (statusHistory.size() >= 2){
                    for (WebSocketListener listener : listeners) {
                        listener.newStatusFired(statusHistory.get(statusHistory.size() - 2), statusHistory.peek());
                    }
                }
            }
        });
    }

    public void clearListeners() {
        listeners.clear();
    }

}
