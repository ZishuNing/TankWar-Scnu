package com.tedu.web;


import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import java.net.*;
import java.io.*;
import java.util.*;
public class Task implements Runnable {
    private int taskId;
    enum TaskType {
        SEND, SERVER_RECEIVE, CLIENT_RECEIVE
    }
    private TaskType taskType;
    private DatagramPacket packet;
    private DatagramSocket serverSocket;
    private GameServer gameServer;
    private GameClient gameClient;
    public Task(int taskId, TaskType ttype, DatagramPacket packet, DatagramSocket serverSocket, GameServer gameServer, GameClient gameClient) {
        this.taskId = taskId;
        this.taskType = ttype;
        this.packet = packet;
        this.serverSocket = serverSocket;
        this.gameServer = gameServer;
        this.gameClient = gameClient;
    }
    public void run() {
        System.out.println("Task #" + taskId + " is running." + taskType);


        switch (taskType) {
            case SERVER_RECEIVE:
                serverReceive();
                break;
            case CLIENT_RECEIVE:
                try {
                    clientReceive();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case SEND:
                try {
                    send();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        System.out.println("Task #" + taskId + " is done.");
    }
    // 服务器和客户端都调用的发送数据的方法
    private void send() throws IOException {
        try{
            System.out.println("sent size:"+packet.getLength());
            serverSocket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void clientReceive() throws IOException, ClassNotFoundException {

        Data recedata = Data.Deserialize(packet.getData());
        if(recedata.type == Data.Type.HELLO_REPLY){
            System.out.println("hello_reply from"+ packet.getAddress() + ":" + packet.getPort());

            gameClient.setPeers(recedata.peers);

            Map<GameElement, List<ElementObj>> game_elements = recedata.gameElements;
            recedata.gameElements.get(GameElement.PLAY).forEach((elementObj)->{
                Play it = (Play) elementObj;
                GameLoad.GameLoadEnum gameLoadEnum = it.getDir();
                if(gameLoadEnum == GameLoad.GameLoadEnum.play1_up || gameLoadEnum == GameLoad.GameLoadEnum.play1_down || gameLoadEnum == GameLoad.GameLoadEnum.play1_left || gameLoadEnum == GameLoad.GameLoadEnum.play1_right){
                    it.setDir(Play.play1ToPlay2(gameLoadEnum));
                    it.setIcon(GameLoad.ImgMap.get(it.getDir()));
                    return;
                }
            });

            ElementManager.setGameElements(recedata.gameElements);

            gameClient.addPeer(new Peer(packet.getAddress(), packet.getPort()));

            int id = recedata.peers.size() +1 ;
            ElementObj elementObj = new Play(0,0,30,30, GameLoad.ImgMap.get(GameLoad.GameLoadEnum.play1_up));
            elementObj.setId(id);

            ElementManager.getManager().addElement(elementObj, GameElement.PLAY);
            Play.setMainPlayId(id);
            gameClient.boardCast((Play) elementObj);
            // 通知主线程可以Client已经加载完毕
            GameWebHelper.lock.lock();
            GameWebHelper.isLoad = true;
            GameWebHelper.loaded.signalAll();
            GameWebHelper.lock.unlock();

        }else if(recedata.type == Data.Type.PLAY_STATUS){
            System.out.println("play_status from"+ packet.getAddress() + ":" + packet.getPort());

            ElementManager.getManager().updatePlayElement(Play.FromString(new String(recedata.data)));

        }else if(recedata.type == Data.Type.ADD_PEER) {
            System.out.println("play_add from" + packet.getAddress() + ":" + packet.getPort());

            gameClient.addPeer(Peer.fromString(new String(recedata.data)));
        }
    }
    // 服务器调用的处理接收到的数据的方法
    private void serverReceive() {

        try {

            InetAddress IPAddress = packet.getAddress();
            int port = packet.getPort();
            byte[] receData = packet.getData();
            Data data = Data.Deserialize(receData);
            if(data.type==Data.Type.HELLO){
                System.out.println("Hello from " + IPAddress + ":" + port);
                // 序列化一个 ElementManager 对象，然后发送给客户端
                Data send_data = new Data(Data.Type.HELLO_REPLY, ElementManager.getManager().getGameElements(), gameServer.getPeers(), null);
                byte[] send = send_data.serialize();
                DatagramPacket sendPacket = new DatagramPacket(send, send.length, IPAddress, port);
                System.out.println("sent size:"+sendPacket.getLength());
                serverSocket.send(sendPacket);
                Peer peer = new Peer(IPAddress, port);
                gameServer.boardCast(peer);
                gameServer.addPeer(peer);

            }else if(data.type == Data.Type.PLAY_STATUS){
                System.out.println("play_status from"+ packet.getAddress() + ":" + packet.getPort());
                ElementManager.getManager().updatePlayElement(Play.FromString(new String(data.data)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}