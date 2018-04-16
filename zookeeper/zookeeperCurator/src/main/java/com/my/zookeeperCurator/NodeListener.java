package com.my.zookeeperCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

public class NodeListener {
    public static void main(String[] args) throws Exception{
        RetryUntilElapsed retryPolciy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.4:2181")
        .connectionTimeoutMs(5000)
        .sessionTimeoutMs(5000)
        .retryPolicy(retryPolciy)
        .build();
        client.start();
        
        NodeCache cache = new NodeCache(client, "/node_1");
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {
            
            @Override
            public void nodeChanged() throws Exception {
                byte[] ret = cache.getCurrentData().getData();
                System.out.println("new Data:"+new String(ret));
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

}
