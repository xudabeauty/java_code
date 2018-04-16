package com.my.ZookeeperClient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class SubscribeChildChanges {
    private static class ZChildListener implements IZkChildListener{

        @Override
        public void handleChildChange(String parentPath, List<String> currentChild) throws Exception {
            System.out.println(parentPath);
            System.out.println(currentChild.toString());
        }
    }
    
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("192.168.2.9:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok");
        zkClient.subscribeChildChanges("/node_1", new ZChildListener());
        Thread.sleep(Integer.MAX_VALUE);
    }

}
