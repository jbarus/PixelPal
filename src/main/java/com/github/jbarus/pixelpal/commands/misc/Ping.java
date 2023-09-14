package com.github.jbarus.pixelpal.commands.misc;

import com.github.jbarus.pixelpal.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Ping implements Command {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Ping pong";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("pong!").queue();
    }
}
