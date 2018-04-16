package com.my.ZookeeperClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class CreateNode {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("192.168.2.9:2181", 10000, 10000, new SerializableSerializer());
        System.out.println("connected");
        User user = new User();
        user.setId(2);
        user.setName("test");
        String path = zkClient.create("/node_4/node_4_4", user, CreateMode.PERSISTENT);
        System.out.println("created path:" + path);
    }

}
