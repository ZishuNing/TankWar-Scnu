package com.tedu.web;
import com.tedu.element.Play;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {

    private WebPool webPool = new WebPool();
    private List<Peer> peers = new ArrayList<>();

    private DatagramSocket serverSocket;

    public synchronized void addPeer(Peer peer) {
        peers.add(peer);
    }

    public void boardCast(Play play) throws IOException {
        // 在这里使用会阻塞自己的线程


        Data data = new Data(Data.Type.PLAY_STATUS, play.ToString().getBytes());


        byte[] sendData = data.serialize();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
        for (Peer peer : peers) {
            sendPacket.setAddress(peer.IPAddress);
            sendPacket.setPort(peer.port);
            webPool.addTask(new Task(2, Task.TaskType.SEND, sendPacket, serverSocket, this));
        }

    }


    public void run(){
        try {
            // 创建一个 DatagramSocket 对象，监听指定的端口
            serverSocket = new DatagramSocket(11451);

            // 创建一个字节数组，用于接收数据
            byte[] receiveData = new byte[1024];

            while (true) {
                // 创建一个 DatagramPacket 对象，用于接收数据
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // 接收数据
                serverSocket.receive(receivePacket);

                webPool.addTask(new Task(1, Task.TaskType.RECEIVE, receivePacket, serverSocket, this));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Peer{
    public Peer(InetAddress IPAddress, int port) {
        this.IPAddress = IPAddress;
        this.port = port;
    }


    public InetAddress IPAddress;
    public int port;
}
