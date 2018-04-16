package com.my.zookeeperCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class DelNode {
    public static void main(String[] args) throws Exception {
        RetryUntilElapsed retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.4:2181")
        .sessionTimeoutMs(5000)
        .connectionTimeoutMs(5000).retryPolicy(retryPolicy).build();
        client.start();
        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath("/node_1/node_1_5");
        Thread.sleep(Integer.MAX_VALUE);
    }

}
