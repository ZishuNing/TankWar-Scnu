package com.tedu.web;
import com.tedu.controller.GameThread;
import com.tedu.element.Play;

import java.io.IOException;
import java.lang.Thread.*;

public class webtest {
    public static void main(String[] args) throws InterruptedException, IOException {

        GameServer server = new GameServer();
        Thread thread = new Thread(server);
        thread.start();



        Thread.sleep(100);
        GameClient client = new GameClient();
        Thread thread2 = new Thread(client);
        thread2.start();
        Thread.sleep(100);
        server.boardCast(new Play(1, 1, 1, 1, null));
    }
}
