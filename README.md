<img align="right" src="https://github.com/jbarus/PixelPal/assets/57799873/50521fde-d834-4c12-8993-a4f9213703d7" width="250" height="auto" >

# PixelPal
Discord bot made with Spring boot, JDA and Lavaplayer

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Available commands](#available-commands)

## General info
Light-weighted Discord bot made for me and my friends. Main reason for creation was situation with YouTube blocking 3rd parties programs. 

## Technologies
Bot is created with:
* Java version: 20.0
* JDA wrapper version: 5.0.0-beta.13
* Lavaplayer library version: 2.0.1
* Spring boot framework version: 3.1.3
* Lombok library version: 1.18.28
* Spring web framework version: 3.1.3

## Setup
Firstly you need to update environment variables located in application.properties file:
```
TOKEN=
```

Then to run this project you can use IntelliJ IDEA or run it locally with commands:
```
mvn package
mvn exec:java
```

## Available commands
Here are the available commands:
* /nowplaying - see currently playing song
* /pause - pause player
* /play - play song
* /queue - queue song
* /repeat - set if current song should repeat after end
* /skip - skip song
* /stop - stop player and empty the queue
