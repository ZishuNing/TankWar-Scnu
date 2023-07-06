package com.tedu.web;


import java.net.*;
import java.io.*;
import java.util.*;
public class Task implements Runnable {
    private int taskId;

    enum TaskType {
        SEND, RECEIVE
    }

    private TaskType taskType;
    private DatagramPacket packet;

    private DatagramSocket serverSocket;
    private GameServer gameServer;

    public Task(int taskId, TaskType ttype, DatagramPacket packet, DatagramSocket serverSocket, GameServer gameServer) {
        this.taskId = taskId;
        this.taskType = ttype;
        this.packet = packet;
        this.serverSocket = serverSocket;
        this.gameServer = gameServer;
    }

    public void run() {
        System.out.println("Task #" + taskId + " is running.");
        if (taskType == TaskType.RECEIVE) {
            receive();
        }else if(taskType == TaskType.SEND){
            try {
                send();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("Task #" + taskId + " is done.");
    }

    private void send() throws IOException {
        try{
            serverSocket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void receive() {

        try {

            // 将接收到的数据原样返回给客户端
            InetAddress IPAddress = packet.getAddress();
            int port = packet.getPort();
            byte[] receData = packet.getData();

            Data data = Data.Deserialize(receData);
            if(data.type==Data.Type.HELLO){
                System.out.println("Hello from " + IPAddress + ":" + port);
                Peer peer = new Peer(IPAddress, port);
                gameServer.addPeer(peer);



                Data senddata = new Data(Data.Type.HELLO_REPLY,null);
                byte[] sendData = senddata.serialize();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}