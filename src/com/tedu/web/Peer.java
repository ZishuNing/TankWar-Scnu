package com.tedu.web;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

class Peer implements Serializable {
    public Peer(InetAddress IPAddress, int port) {
        this.IPAddress = IPAddress;
        this.port = port;
    }

    public String toString() {
        return IPAddress.getHostName() + ":" + port;
    }

    public static Peer fromString(String str) throws UnknownHostException {
        String[] strs = str.split(":");
        return new Peer(InetAddress.getByName(strs[0]), Integer.parseInt(strs[1]));
    }

    public InetAddress IPAddress;
    public int port;
}