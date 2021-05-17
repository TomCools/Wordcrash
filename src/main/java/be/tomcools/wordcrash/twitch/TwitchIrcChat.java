package be.tomcools.wordcrash.twitch;

import be.tomcools.wordcrash.domain.ChatMessageListener;
import be.tomcools.wordcrash.domain.IRCChatProvider;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // We want a new instance each time.
public class TwitchIrcChat implements IRCChatProvider {
    private PircBotX bot;

    @Value("twitch.auth.token")
    private String authToken;

    @Override
    @Async //PircBotX blocks the thread when it's started. So @Async creates a new thread!
    public void connect(String channel, ChatMessageListener listener) {
        Configuration configuration = new Configuration.Builder()
                .setAutoNickChange(false) //Twitch doesn't support multiple users
                .setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command
                //.setCapEnabled(true)
                //+.addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                //Twitch by default doesn't send JOIN, PART, and NAMES unless you request it, see https://dev.twitch.tv/docs/irc/guide/#twitch-irc-capabilities
                //.addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                .addServer("euroserv.fr.quakenet.org")
                .setName("WORDBOT") //Your twitch.tv username, used from token
                // .setServerPassword(authToken) //Your oauth password from http://twitchapps.com/tmi
                .addAutoJoinChannel("#" + channel) //Some twitch channel
                .addListener(new TwitchIrcChatListener(listener)).buildConfiguration();

        bot = new PircBotX(configuration);
        //Connect to the server
        try {
            bot.startBot();
        } catch (Exception e) {
            throw new RuntimeException("Could not start the bot!", e);
        }
    }

    @Async
    @Override
    public void disconnect() {
        bot.close();
    }
}
