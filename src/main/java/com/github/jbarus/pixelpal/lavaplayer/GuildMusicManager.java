package com.github.jbarus.pixelpal.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;

@Getter
public class GuildMusicManager {
    private TrackScheduler trackScheduler;
    private AudioPlayerSendHandler sendHandler;

    public GuildMusicManager (AudioPlayerManager manager){
        AudioPlayer player = manager.createPlayer();
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        sendHandler = new AudioPlayerSendHandler(player);
    }
}
