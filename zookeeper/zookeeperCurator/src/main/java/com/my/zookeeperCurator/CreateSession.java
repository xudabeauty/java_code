package com.my.zookeeperCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class CreateSession {
    public static void main(String[] args) throws Exception{
        RetryUntilElapsed retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.9:2181")
        .sessionTimeoutMs(5000).retryPolicy(retryPolicy).build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
