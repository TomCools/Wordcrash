package be.tomcools.wordcrash.web;

import be.tomcools.wordcrash.camel.TwitchChatBridgeFactory;
import be.tomcools.wordcrash.web.helpers.SessionEventHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class SocketController {

    @Autowired
    TwitchChatBridgeFactory factory;


    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) throws Exception {
        String twitchChannel = SessionEventHelper.extractChannel(event);

        factory.registerTwitchBridge(twitchChannel);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {

    }


}