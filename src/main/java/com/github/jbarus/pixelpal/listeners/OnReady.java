package com.github.jbarus.pixelpal.listeners;

import com.github.jbarus.pixelpal.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OnReady extends ListenerAdapter {
    private final List<Command> commandList;

    public OnReady(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void onReady(ReadyEvent event) {
        for(Guild guild : event.getJDA().getGuilds()){
            for(Command command : this.commandList){
                if(command.getOptions() == null){
                    guild.upsertCommand(command.getName(),command.getDescription()).queue();
                }else{
                    guild.upsertCommand(command.getName(),command.getDescription()).addOptions(command.getOptions()).queue();
                }
            }
        }
    }
}
