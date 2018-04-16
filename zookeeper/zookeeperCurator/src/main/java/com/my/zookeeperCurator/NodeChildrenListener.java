package com.my.zookeeperCurator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

public class NodeChildrenListener {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("192.168.2.4:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        
        client.start();
        PathChildrenCache cache = new PathChildrenCache(client, "/node_1", true);
        cache.start();
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED:"+event.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATE:"+event.getData());
                        break;
                    case CHILD_REMOVED:
                    System.out.println("CHILD_REMOVE"+event.getData());
                    break;
                    default:
                        break;
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

}
