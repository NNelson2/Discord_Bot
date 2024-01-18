package noah.nelson.listeners;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import noah.nelson.lavaplayer.PlayerManager;

import javax.annotation.Nonnull;

public class MusicCommandListener extends ListenerAdapter {


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String prefix = "!";
        final MessageChannelUnion channel = event.getChannel();
        String raw = event.getMessage().getContentRaw();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        if (raw.startsWith(prefix+"play")) {
            if (!memberVoiceState.inAudioChannel()) {
                channel.sendMessage("You need to be in a voice channel for this command to work").queue();
                return;
            }
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final AudioChannelUnion memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);
            System.out.println(event.getMessage().getContentRaw().replace("!play ", ""));
            PlayerManager.getInstance().loadAndPlay(channel.asTextChannel(), event.getMessage().getContentRaw().replace("!play ", ""));
        } 
    }
}
