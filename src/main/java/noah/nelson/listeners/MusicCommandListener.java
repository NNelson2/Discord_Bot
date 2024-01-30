package noah.nelson.listeners;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import noah.nelson.lavaplayer.GuildMusicManager;
import noah.nelson.lavaplayer.PlayerManager;

import javax.annotation.Nonnull;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public class MusicCommandListener extends ListenerAdapter {


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String prefix = "!";
        String joinChannelString = "nah, you need to join a chat channel first";
        final MessageChannelUnion channel = event.getChannel();
        String raw = event.getMessage().getContentRaw();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        if (raw.startsWith(prefix+"play")) {
            if (!memberVoiceState.inAudioChannel()) {
                channel.sendMessage(joinChannelString).queue();
                return;
            }
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final AudioChannelUnion memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);
            System.out.println(event.getMessage().getContentRaw().replace("!play ", ""));
            PlayerManager.getInstance().loadAndPlay(channel.asTextChannel(), event.getMessage().getContentRaw().replace("!play ", ""));
        } 
        if (raw.startsWith(prefix+"skip")) {
            if (!memberVoiceState.inAudioChannel()) {
                channel.sendMessage(joinChannelString);
                return;
            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            if (audioPlayer.getPlayingTrack() == null) {
                channel.sendMessage("nothing currently playing");
                return;
            }
            musicManager.scheduler.nextTrack();
        }
    }
}
