package be.tomcools.wordcrash.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TwitchChatBridgeFactory {

    private final SimpMessagingTemplate template;
    private final CamelContext context;
    private final String token;

    @Autowired
    public TwitchChatBridgeFactory(@Value("${twitch.auth.token}") String token,
                                   CamelContext context,
                                   SimpMessagingTemplate template) {
        this.token = token;
        this.template = template;
        this.context = context;
    }

    public void registerTwitchBridge(String twitchChannel) throws Exception {
        TwitchChatBridge bridge = new TwitchChatBridge(twitchChannel, template, token);
        if(context.getRoutes().stream().noneMatch(c -> c.getRouteId().equals(twitchChannel))) {
            // Add a new route
            log.info("Creating new Twitch IRC Bridge for channel: {}", twitchChannel);
            context.addRoutes(bridge);
            log.info("Route added for channel: {}", twitchChannel);
        } else {
            log.info("Reusing existing Twitch IRC Bridge for channel: {}", twitchChannel);
        }
    }
}
