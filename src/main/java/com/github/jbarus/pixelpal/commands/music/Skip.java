package com.github.jbarus.pixelpal.commands.music;

import com.github.jbarus.pixelpal.commands.Command;
import com.github.jbarus.pixelpal.lavaplayer.GuildMusicManager;
import com.github.jbarus.pixelpal.lavaplayer.PlayerManager;
import com.github.jbarus.pixelpal.lavaplayer.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.BlockingQueue;
@Component
public class Skip implements Command {
    private final PlayerManager playerManager;

    public Skip(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "Will skip desired number of songs";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.INTEGER,"number","number of songs to skip, leave blank to skip one",false));
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

        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("You need to be in the same channel as me").queue();
            return;
        }

        int numberOfSongs = event.getOption("number",1, OptionMapping::getAsInt);
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
        AudioPlayer player = trackScheduler.getPlayer();
        BlockingQueue<AudioTrack> queue = trackScheduler.getQueue();
        if(numberOfSongs>queue.size()){
            numberOfSongs = queue.size() + 1;
        }
        for (int i = 0; i < numberOfSongs-1; i++) {
            queue.poll();
        }
        player.stopTrack();
        event.reply("Skipped " + numberOfSongs + " songs!").queue();
    }
}
