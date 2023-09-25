package com.github.jbarus.pixelpal.commands.music;

import com.github.jbarus.pixelpal.commands.Command;
import com.github.jbarus.pixelpal.lavaplayer.GuildMusicManager;
import com.github.jbarus.pixelpal.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Pause implements Command {
    private final PlayerManager playerManager;

    public Pause(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getDescription() {
        return "Will pause/unpause track";
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
        boolean isPaused = guildMusicManager.getPlayer().isPaused();
        guildMusicManager.getPlayer().setPaused(!isPaused);
        if(!isPaused)
            event.reply("Track is now stopped").queue();
        else
            event.reply("Track is now resumed").queue();

    }
}
