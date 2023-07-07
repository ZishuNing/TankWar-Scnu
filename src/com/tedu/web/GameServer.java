package com.tedu.web;

import com.tedu.element.Play;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer extends Thread{

    private WebPool webPool = new WebPool(8);
    private List<Peer> peers = new ArrayList<>();

    private DatagramSocket serverSocket;


    public synchronized void addPeer(Peer peer) {
        peers.add(peer);
    }

    public synchronized List<Peer> getPeers() {
        return peers;
    }

    public void boardCast(Peer peer) {
        Data data = new Data(Data.Type.ADD_PEER, null, null, peer.toString().getBytes());
        try {
            byte[] sendData = data.serialize();
            for (Peer each_peer : peers) {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
                sendPacket.setAddress(each_peer.IPAddress);
                sendPacket.setPort(each_peer.port);
                webPool.addTask(new Task(2, Task.TaskType.SEND, sendPacket, serverSocket, this,null));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void boardCast(Play play) throws IOException {
        // 在这里使用会阻塞自己的线程

//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                Data data = new Data(Data.Type.PLAY_STATUS, play.ToString().getBytes());
//
//
//                byte[] sendData;
//                try {
//                    sendData = data.serialize();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
//                for (Peer peer : peers) {
//                    executor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            sendPacket.setAddress(peer.IPAddress);
//                            sendPacket.setPort(peer.port);
//                            try{
//                                serverSocket.send(sendPacket);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });
        Data data = new Data(Data.Type.PLAY_STATUS, null, null, play.ToString().getBytes());
        try {
            byte[] sendData = data.serialize();
            for (Peer peer : peers) {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
                sendPacket.setAddress(peer.IPAddress);
                sendPacket.setPort(peer.port);
                webPool.addTask(new Task(2, Task.TaskType.SEND, sendPacket, serverSocket, this,null));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void run() {
//        executor = Executors.newFixedThreadPool(8);

        System.out.println("GameServer is running...");


        try {


            // 创建一个 DatagramSocket 对象，监听指定的端口
            serverSocket = new DatagramSocket(11451);

            // 创建一个字节数组，用于接收数据
            byte[] receiveData = new byte[10000];

            while (true) {
                // 创建一个 DatagramPacket 对象，用于接收数据
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // 接收数据
                serverSocket.receive(receivePacket);



                webPool.addTask(new Task(1, Task.TaskType.SERVER_RECEIVE, receivePacket, serverSocket, this,null));



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


