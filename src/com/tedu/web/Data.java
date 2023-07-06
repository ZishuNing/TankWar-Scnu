package com.tedu.web;

import java.io.*;

public class Data implements Serializable{// 存放交换数据的类
    enum Type{
        HELLO,// 客户端向服务器发送的第一个数据包，广播的数据包
        HELLO_REPLY,// 服务器向客户端回复
        PLAY_STATUS,// 客户端向服务器发送的游戏状态数据包
    }

    public static Data Deserialize(byte[] serialData) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(serialData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Data data = (Data) ois.readObject();
        return data;
    }


    public Data(Type type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        return baos.toByteArray();
    }



    public Type type;
    public byte[] data;
}
