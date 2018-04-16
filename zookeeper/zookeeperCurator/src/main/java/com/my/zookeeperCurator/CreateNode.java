package com.my.zookeeperCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

public class CreateNode {
    public static void main(String[] args) throws Exception {
        RetryUntilElapsed retryPoicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.4:2181")
        .sessionTimeoutMs(5000).connectionTimeoutMs(5000).retryPolicy(retryPoicy).build();
        client.start();
        
        String path=client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
              .forPath("/node_1/node_4","123".getBytes());
        System.out.println(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
