package com.my.zookeeperCurator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

public class CheckEexists {
    public static void main(String[] args) throws Exception {
        ExecutorService eService=Executors.newFixedThreadPool(5);
        RetryUntilElapsed retyrPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.4:2181").sessionTimeoutMs(5000)
        .connectionTimeoutMs(5000).retryPolicy(retyrPolicy).build();
        client.start();
        client.checkExists().inBackground(new BackgroundCallback() {
            
            @Override
            public void processResult(CuratorFramework arg0, CuratorEvent event) throws Exception {
                Stat stat = event.getStat();
                System.out.println(stat);
                System.out.println(event.getContext());
            }
        },"666",eService).forPath("/node_1");
        Thread.sleep(Integer.MAX_VALUE);
    }
    

}
