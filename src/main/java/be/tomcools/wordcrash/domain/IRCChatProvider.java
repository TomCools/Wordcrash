package be.tomcools.wordcrash.domain;

public interface IRCChatProvider {
    void connect(String channel, ChatMessageListener listener);

    void disconnect();
}
