package com.github.jbarus.pixelpal.listeners;

import com.github.jbarus.pixelpal.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
public class OnSlashCommand extends ListenerAdapter {
    private final List<Command> commandList;
    private final ExecutorService executorService;

    public OnSlashCommand(List<Command> commandList, ExecutorService executorService) {
        this.commandList = commandList;
        this.executorService = executorService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        for(Command command : commandList){
            if(command.getName().equals(event.getName())){
                executorService.submit(()->{
                    command.execute(event);
                });
                return;
            }
        }
    }
}
