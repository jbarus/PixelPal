package com.github.jbarus.pixelpal.listeners;

import com.github.jbarus.pixelpal.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OnSlashCommand extends ListenerAdapter {
    private final List<Command> commandList;

    public OnSlashCommand(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        for(Command command : commandList){
            if(command.getName().equals(event.getName())){
                command.execute(event);
                return;
            }
        }
    }
}
