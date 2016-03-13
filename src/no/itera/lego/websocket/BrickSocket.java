package no.itera.lego.websocket;

import no.itera.lego.util.RobotState;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BrickSocket extends WebSocketClient {

    private final RobotState state;

    public BrickSocket(String url, RobotState state) {
        super(URI.create(url));
        this.state = state;
    }

    @Override
    public void connect(){
        this.state.webSocketConnecting = true;
        super.connect();
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        this.state.webSocketConnecting = false;
        state.webSocketOpen = true;
        System.out.println("Connected");
    }

    @Override
    public void onMessage(String s) {
        state.lastMessage = s;
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Disconnected");
        state.webSocketOpen = false;
    }

    @Override
    public void onError(Exception e) {
        System.out.println(e);
    }
}
