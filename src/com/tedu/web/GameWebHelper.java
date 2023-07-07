package com.tedu.web;

import com.tedu.element.Play;

import java.io.IOException;

public class GameWebHelper {

    public static GameClient gameClient;
    public static GameServer gameServer;

    public static void setGameClient(GameClient game_client) {
        gameClient = game_client;
    }

    public static void setGameServer(GameServer game_server) {
        gameServer = game_server;
    }



    public static void boardCast(Play play){
        if(gameServer != null){
            try {
                gameServer.boardCast(play);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(gameClient != null){
            try {
                gameClient.boardCast(play);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
