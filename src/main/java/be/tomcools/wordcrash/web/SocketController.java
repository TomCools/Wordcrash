package be.tomcools.wordcrash.web;

import be.tomcools.wordcrash.domain.ChatBridge;
import be.tomcools.wordcrash.web.helpers.ChannelSessionCache;
import be.tomcools.wordcrash.web.helpers.SessionEventHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class SocketController {

    private final SimpMessagingTemplate template;
    private final ChatBridge chatBridge;
    private final ChannelSessionCache channelSessionCache;

    @Autowired
    public SocketController(SimpMessagingTemplate template, ChatBridge chatBridge, ChannelSessionCache channelSessionCache) {
        this.template = template;
        this.chatBridge = chatBridge;
        this.channelSessionCache = channelSessionCache;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        String channel = SessionEventHelper.extractChannel(event);
        String sessionId = SessionEventHelper.extractSessionId(event);

        // Save combination of channel and sessionID, this is the only place where we'll have both at the same time.
        channelSessionCache.add(channel, sessionId);
        chatBridge.start(channel, chatMessage -> {
            // Whenever a message arrives, send it to /topic/CHANNEL_NAME through STOMP
            template.convertAndSend("/topic/" + channel, chatMessage);
        });
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        // Get the channel from the cache based on the sessionId.
        String channel = channelSessionCache.getChannelForSession(event.getSessionId());
        chatBridge.stop(channel);
        channelSessionCache.delete(event.getSessionId());
    }

    /* Not used in this project, but you could also use Spring Messaging annotations to work with STOMP
    Leaving it in here for reference :-)

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(SimpMessageHeaderAccessor headerAccessor, HelloMessage message) throws Exception {
        Thread.sleep(100); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!" + headerAccessor.getSessionId() + ": " + headerAccessor.getSubscriptionId());
    }
    */

}
