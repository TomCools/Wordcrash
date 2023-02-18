package be.tomcools.wordcrash.camel;

import be.tomcools.wordcrash.domain.ChatMessage;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.irc.IrcMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class TwitchChatBridge extends RouteBuilder {

    private final String channel;
    private final String authToken;
    private final SimpMessagingTemplate template;

    public TwitchChatBridge(String channel, SimpMessagingTemplate template, String authToken) {
        this.channel = channel;
        this.template = template;
        this.authToken = authToken;
    }

    public void configure() {
        String twitchIrcUrl = "irc://irc.twitch.tv?nickname=*&password=" + authToken + "&channels=" + channel;

        from(twitchIrcUrl)
                .routeId(channel)
                .filter(e -> e.getMessage(IrcMessage.class).getTarget().equals("#" + channel))
                .process(exchange -> {
                    IrcMessage message = exchange.getMessage(IrcMessage.class);
                    ChatMessage chat = new ChatMessage(message.getUser().getUsername(), message.getMessage());
                    template.convertAndSend("/topic/" + channel, chat);
                });
    }

}
