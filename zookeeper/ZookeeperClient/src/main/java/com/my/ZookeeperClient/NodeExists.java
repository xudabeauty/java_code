package com.my.ZookeeperClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class NodeExists {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("192.168.2.4:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok");
        boolean exists = zkClient.exists("/node_4");
        System.out.println(exists);
    }
}
