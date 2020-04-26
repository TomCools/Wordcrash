package be.tomcools.wordcrash.domain;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatBridgeImpl implements ChatBridge {

    private final ObjectProvider<IRCChatProvider> chatProvider;
    private final ChatCache cache;

    @Autowired
    public ChatBridgeImpl(ObjectProvider<IRCChatProvider> chatProvider, ChatCache cache) {
        this.chatProvider = chatProvider;
        this.cache = cache;
    }

    @Override
    public void start(String channel, ChatMessageListener listener) {
        // Objectprovider because we always want a new instance.
        IRCChatProvider chat = chatProvider.getObject();
        chat.connect(channel, listener);

        this.cache.add(channel, chat);
    }

    @Override
    public void stop(String channel) {
        IRCChatProvider chatProvider = this.cache.get(channel);
        chatProvider.disconnect();

        this.cache.remove(channel);
    }
}
