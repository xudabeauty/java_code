package com.my.zookeeperCurator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class GetChild {
    public static void main(String[] args)  throws Exception{
        RetryUntilElapsed retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.4:2181")
        .sessionTimeoutMs(5000)
        .sessionTimeoutMs(5000)
        .retryPolicy(retryPolicy).build();
        client.start();
        List<String> list= client.getChildren().forPath("/node_1");
        System.out.println(list);
    }

}
