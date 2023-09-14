package com.github.jbarus.pixelpal.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class DefaultConfig {
    @Value("${TOKEN}")
    private static String token;
    @Bean
    public static JDA jda(List<ListenerAdapter> listeners){
        if(token == null){
            token = System.getenv("token");
        }
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(listeners.toArray(new ListenerAdapter[0]));
        return jda;
    }
}
