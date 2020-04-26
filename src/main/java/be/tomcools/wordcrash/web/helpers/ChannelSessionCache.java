package be.tomcools.wordcrash.web.helpers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
 This cache will contain the link between a Spring Session and a given Twitch Channel.
 */
@Component
public class ChannelSessionCache {
    private final List<SessionInfo> CACHE = new ArrayList<>();

    public void add(String channel, String session) {
        CACHE.add(new SessionInfo(channel, session));
    }

    public String getSessionForChannel(String channel) {
        return CACHE.stream().filter(s -> channel.equals(s.channel()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Couldn't get session for channel"))
                .session();
    }

    public String getChannelForSession(String session) {
        return CACHE.stream().filter(s -> session.equals(s.session()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Couldn't get channel for session"))
                .channel();
    }

    public void delete(String sessionOrChannel) {
        CACHE.removeIf(e -> e.session().equals(sessionOrChannel) || e.channel().equals(sessionOrChannel));
    }
}
