package be.tomcools.wordcrash.domain;

public interface ChatMessageListener {
    void onChatMessageReceived(ChatMessage chatMessage);
}
