package be.tomcools.wordcrash.web.helpers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChannelSessionCache {
    private List<SessionInfo> CACHE = new ArrayList<>();

    public void add(String channel, String session) {
        CACHE.add(new SessionInfo(channel, session));
    }

    public String getSessionForChannel(String channel) {
        return CACHE.stream().filter(s -> channel.equals(s.getChannel()))
                .findFirst().get().getSession();
    }

    public String getChannelForSession(String session) {
        return CACHE.stream().filter(s -> session.equals(s.getSession()))
                .findFirst().get().getChannel();
    }

    public void delete(String sessionOrChannel) {
        CACHE.removeIf(e -> e.getSession().equals(sessionOrChannel) || e.getChannel().equals(sessionOrChannel));
    }
}
