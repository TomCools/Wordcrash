package be.tomcools.wordcrash.twitch;

import be.tomcools.wordcrash.domain.ChatMessage;
import be.tomcools.wordcrash.domain.ChatMessageListener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class TwitchIrcChatListener extends ListenerAdapter {
    private final ChatMessageListener listener;

    public TwitchIrcChatListener(final ChatMessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        listener.onChatMessageReceived(new ChatMessage(event.getUser().getNick(), event.getMessage()));

        //When someone says ?helloworld respond with "Hello World"
        if (event.getMessage().startsWith("?helloworld"))
            event.respond("Hello world!");
    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        super.onConnect(event);
        // Do something on connect
    }
}
