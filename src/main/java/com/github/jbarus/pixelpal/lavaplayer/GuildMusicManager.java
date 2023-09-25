package com.github.jbarus.pixelpal.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;

@Getter
public class GuildMusicManager {
    private TrackScheduler trackScheduler;
    private AudioPlayerSendHandler sendHandler;
    private AudioPlayer player;

    public GuildMusicManager (AudioPlayerManager manager, Guild guild){
        player = manager.createPlayer();
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        sendHandler = new AudioPlayerSendHandler(player,guild);
    }
}
