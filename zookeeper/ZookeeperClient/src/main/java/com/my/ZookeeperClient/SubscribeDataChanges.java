package com.my.ZookeeperClient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class SubscribeDataChanges {
    private static class ZkDataListener implements IZkDataListener{

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            System.out.println(dataPath+":"+data.toString());
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            System.out.println(dataPath);
        }
    }

    
    public static void main(String[] args) throws InterruptedException {
       ZkClient client= new ZkClient("192.168.2.4:2181",10000,10000,new BytesPushThroughSerializer());
        System.out.println("connected");
       client.subscribeDataChanges("/node_1", new ZkDataListener());
       Thread.sleep(Integer.MAX_VALUE);
    }
}
