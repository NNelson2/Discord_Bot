package noah.nelson;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import noah.nelson.listeners.MusicCommandListener;

import javax.security.auth.login.LoginException;

public class Bot {

    private final ShardManager shardManager;
    public Bot() throws LoginException {
        String token = System.getenv("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGES,GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching(""));
        shardManager = builder.build();
        shardManager.addEventListener(new MusicCommandListener());
    }

    public ShardManager getShardManager() {
        return shardManager;
    }
    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
        } catch (LoginException e) {
            System.out.println("Login error");
        }

    }
}
