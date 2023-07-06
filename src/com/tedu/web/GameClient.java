package com.tedu.web;
import com.tedu.element.Play;

import java.net.*;
public class GameClient extends Thread{
    public void run() {
        try {
            // 创建一个 DatagramSocket 对象
            DatagramSocket clientSocket = new DatagramSocket();

            // 创建一个字节数组，用于发送数据
            Data data = new Data(Data.Type.HELLO, "".getBytes());
            byte[] sendData = data.serialize();

            Data data2 = Data.Deserialize(sendData);
//            System.out.println(data2.type);

            // 创建一个 DatagramPacket 对象，用于发送数据
//            InetAddress IPAddress = InetAddress.getByName("localhost");

            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");

            int port = 11451;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            // 发送数据
            clientSocket.send(sendPacket);

            // 创建一个字节数组，用于接收数据
            byte[] receiveData = new byte[1024];

            // 创建一个 DatagramPacket 对象，用于接收数据
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // 接收数据
            while(true){
                clientSocket.receive(receivePacket);
                Data recedata = Data.Deserialize(receivePacket.getData());
                if(recedata.type == Data.Type.HELLO_REPLY){
                    System.out.println("hello_reply from"+ receivePacket.getAddress() + ":" + receivePacket.getPort());
                }else if(recedata.type == Data.Type.PLAY_STATUS){

                    String playString = new String(recedata.data);
                    System.out.println("play_status from"+ receivePacket.getAddress() + ":" + receivePacket.getPort() + playString);
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
