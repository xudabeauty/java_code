package com.my.zookeeperCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

public class GetData {
    public static void main(String[] args) throws Exception{
        RetryUntilElapsed retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder()
        .connectString("192.168.2.4:2181")
        .connectionTimeoutMs(5000)
        .sessionTimeoutMs(5000).retryPolicy(retryPolicy)
        .build();
        client.start();
        Stat stat = new Stat();
        byte[] ret = client.getData().storingStatIn(stat).forPath("/node_1");
        System.out.println(new String(ret));
        System.out.println(stat);
    }

}
