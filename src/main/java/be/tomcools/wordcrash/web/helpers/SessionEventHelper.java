package be.tomcools.wordcrash.web.helpers;

import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.List;
import java.util.Map;

public class SessionEventHelper {

    // Used to extract the channel from the initial connection
    public static String extractChannel(AbstractSubProtocolEvent event) {
        return ((Map<String, List<String>>) event.getMessage().getHeaders().get("nativeHeaders")).get("channel").get(0);
    }

    public static String extractSessionId(AbstractSubProtocolEvent event) {
        return (String) event.getMessage().getHeaders().get("simpSessionId");
    }
}
