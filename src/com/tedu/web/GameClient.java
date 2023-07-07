package com.tedu.web;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameClient extends Thread{


    private Peer peer;
    DatagramSocket clientSocket;
    boolean isConnect = false;
    WebPool webPool = new WebPool(4);

    private List<Peer> peers = new ArrayList<>();

    public synchronized void addPeer(Peer peer) {
        peers.add(peer);
    }

    public synchronized void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

    public void boardCast(Play play) throws IOException {
        // 在这里使用会阻塞自己的线程


        Data data = new Data(Data.Type.PLAY_STATUS, null, null, play.ToString().getBytes());



        try {
            byte[] sendData = data.serialize();
            for (Peer peer : peers) {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
                sendPacket.setAddress(peer.IPAddress);
                sendPacket.setPort(peer.port);
                webPool.addTask(new Task(2, Task.TaskType.SEND, sendPacket, clientSocket, null,this));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

    public void run() {
        System.out.println("GameClient start");

        try {
            // 创建一个 DatagramSocket 对象
            clientSocket = new DatagramSocket();

            // 创建一个字节数组，用于发送数据
            Data data = new Data(Data.Type.HELLO);
            byte[] sendData = data.serialize();


            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");

            int port = 11451;


            Lock lock = new ReentrantLock();




            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    while(!isConnect){
                        try {
                            lock.unlock();
                            System.out.println("send hello");
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                            // 发送数据
                            clientSocket.send(sendPacket);
                            Thread.sleep(2000); // 睡眠一秒后再次发送
                            lock.lock();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    lock.unlock();
                }
            });
            thread.start();


            while(true){


                byte[] receiveData = new byte[100000];


                // 创建一个 DatagramPacket 对象，用于接收数据
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                clientSocket.receive(receivePacket);

                System.out.println("receive from"+ receivePacket.getAddress() + ":" + receivePacket.getPort() +"size:" + receivePacket.getLength());
                // 释放循环线程
                lock.lock();
                isConnect = true;
                lock.unlock();

                System.out.println("receive from"+ receivePacket.getAddress() + ":" + receivePacket.getPort() +"size:" + receivePacket.getLength());

                webPool.addTask(new Task(3, Task.TaskType.CLIENT_RECEIVE,receivePacket, clientSocket, null,this));
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
