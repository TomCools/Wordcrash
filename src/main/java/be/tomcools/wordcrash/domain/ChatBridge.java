package be.tomcools.wordcrash.domain;

public interface ChatBridge {
    void start(String channel, ChatMessageListener listener);

    void stop(String channel);
}
