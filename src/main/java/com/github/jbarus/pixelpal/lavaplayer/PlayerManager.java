package com.github.jbarus.pixelpal.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class PlayerManager {
    private final AudioPlayerManager audioPlayerManager;
    private Map<Long, GuildMusicManager> guildMusicManagers = new HashMap<>();

    public PlayerManager(AudioPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
    }


    public GuildMusicManager getGuildMusicManager(Guild guild){
        return guildMusicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            GuildMusicManager musicManager = new GuildMusicManager(audioPlayerManager);

            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

            return musicManager;
        });
    }

    public void play(Guild guild, String trackURL){
        GuildMusicManager guildMusicManager = getGuildMusicManager(guild);
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                guildMusicManager.getTrackScheduler().queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack audioTrack : playlist.getTracks()){
                    guildMusicManager.getTrackScheduler().queue(audioTrack);
                }
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }
}
