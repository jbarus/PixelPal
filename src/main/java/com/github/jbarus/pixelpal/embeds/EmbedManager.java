package com.github.jbarus.pixelpal.embeds;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
@Component
public class EmbedManager {
    HashMap<String, AudioTrack> tracksForEmbed = new HashMap<>();
    ExecutorService executorService = Executors.newCachedThreadPool();

    public Future<MessageEmbed> getEmbed(String trackURL){
        return executorService.submit(()->{
            AudioTrack track = null;
            EmbedBuilder embedBuilder = new EmbedBuilder();
            while(track == null){
                track = tracksForEmbed.remove(trackURL);
                Thread.sleep(200);
            }
            embedBuilder.setTitle("Now will start playing:");
            embedBuilder.setDescription("[" + track.getInfo().title + "]" + "(" + track.getInfo().uri + ")");
            embedBuilder.setThumbnail(track.getInfo().artworkUrl);
            return embedBuilder.build();
        });
    }

    public MessageEmbed getEmbedForTrack(AudioTrack track){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Now playing:");
        embedBuilder.setDescription("[" + track.getInfo().title + "]" + "(" + track.getInfo().uri + ")");
        embedBuilder.setThumbnail(track.getInfo().artworkUrl);
        return embedBuilder.build();
    }

    public  MessageEmbed getEmbedForQueue(List<AudioTrack> queue){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Current queue:");
        int numberOfTracks = queue.size();
        if(numberOfTracks>25)
            numberOfTracks=25;
        for(int i = 0; i < numberOfTracks; i++){
            AudioTrackInfo info = queue.get(i).getInfo();
            embedBuilder.addField(" ",i+1 + "." + info.title, false);
        }
        if(queue.size()>25){
            int playlistSize = queue.size()-25;
            String message = "And " + playlistSize + " more";
            embedBuilder.setFooter(message);
        }
        return embedBuilder.build();
    }

    public void addTrack(String trackUrl, AudioTrack track){
        tracksForEmbed.put(trackUrl,track);

    }
}
