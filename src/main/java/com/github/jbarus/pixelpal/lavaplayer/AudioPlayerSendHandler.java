package com.github.jbarus.pixelpal.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;

import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer player;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);
    private final MutableAudioFrame frame = new MutableAudioFrame();
    private final Guild guild;
    private int time = 0;


    public AudioPlayerSendHandler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
        frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        boolean canProvide = player.provide(frame);
        if(!canProvide){
            time += 20;
            if(time >= 600000){
                time = 0;
                guild.getAudioManager().closeAudioConnection();
            }
        }else{
            time = 0;
        }
        return canProvide;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return buffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
