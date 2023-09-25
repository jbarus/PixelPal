package com.github.jbarus.pixelpal.commands.music;

import com.github.jbarus.pixelpal.commands.Command;
import com.github.jbarus.pixelpal.embeds.EmbedManager;
import com.github.jbarus.pixelpal.lavaplayer.GuildMusicManager;
import com.github.jbarus.pixelpal.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class Queue implements Command {
    private final PlayerManager playerManager;
    private final EmbedManager embedManager;

    public Queue(PlayerManager playerManager, EmbedManager embedManager) {
        this.playerManager = playerManager;
        this.embedManager = embedManager;
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "Will show the queued songs";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
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

        if(!selfVoiceState.inAudioChannel()){
            event.reply("I am not in an audio channel").queue();
            return;
        }

        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()){
            event.reply("You are not in the same channel as me").queue();
            return;
        }

        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
        List<AudioTrack> queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
        if (queue.isEmpty()){
            event.reply("Queue is empty").queue();
            return;
        }
        event.replyEmbeds(embedManager.getEmbedForQueue(queue)).queue();

    }
}
