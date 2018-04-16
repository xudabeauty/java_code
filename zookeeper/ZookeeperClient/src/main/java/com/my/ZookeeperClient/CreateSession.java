package com.my.ZookeeperClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class CreateSession {
    public static void main(String[] args) {
        ZkClient client=new ZkClient("192.168.2.4:2181",10000,10000,new SerializableSerializer());
        System.out.println("connect ok");
        User user=new User();
        user.setId(1);
        user.setName("test");
        String path=client.create("/node_4", user, CreateMode.PERSISTENT);
        System.out.println("created path:"+path);
    }
}
