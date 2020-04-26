package be.tomcools.wordcrash.web.helpers;

public class SessionInfo {
    private final String channel;
    private final String session;

    public SessionInfo(String channel, String session) {
        this.channel = channel;
        this.session = session;
    }

    public String getChannel() {
        return channel;
    }

    public String getSession() {
        return session;
    }
}
