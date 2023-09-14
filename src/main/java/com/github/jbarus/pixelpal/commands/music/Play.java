package com.github.jbarus.pixelpal.commands.music;

import com.github.jbarus.pixelpal.commands.Command;
import com.github.jbarus.pixelpal.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
        event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        String name = event.getOption("name").getAsString();
        playerManager.play(event.getGuild(), name);
        event.reply("Playing").queue();
    }
}
