package be.tomcools.wordcrash.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatCache {
    private final Map<String, IRCChatProvider> CHAT_MAP = new HashMap<>();

    public void add(String channel, IRCChatProvider chatProvider) {
        this.CHAT_MAP.put(channel, chatProvider);
    }

    public IRCChatProvider get(String channel) {
        return CHAT_MAP.get(channel);
    }

    public void remove(String channel) {
        CHAT_MAP.remove(channel);
    }
}
