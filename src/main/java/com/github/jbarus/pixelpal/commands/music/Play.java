package com.github.jbarus.pixelpal.commands.music;

import com.github.jbarus.pixelpal.commands.Command;
import com.github.jbarus.pixelpal.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class Play implements Command {

    private final PlayerManager playerManager;

    public Play(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Will play a song";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING,"name","YouTube link",true));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel").queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        } else {
            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("You need to be in the same channel as me").queue();
                return;
            }
        }

        String name = event.getOption("name").getAsString();

        Future<Void> future = playerManager.play(event.getGuild(), name, event.getIdLong());
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        AudioTrack track = playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getTracksToEmbed().remove(event.getIdLong());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Now will start playing:");
        embedBuilder.setDescription("[" + track.getInfo().title + "]" + "(" + track.getInfo().uri + ")");
        embedBuilder.setThumbnail(track.getInfo().artworkUrl);

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
