package com.my.zookeeperCurator;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class CreateNodeAuth {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.2.4:2181")
        .sessionTimeoutMs(5000)
        .connectionTimeoutMs(5000).retryPolicy(retryPolicy).build();
        client.start();
        
        ACL aclip = new ACL(Perms.READ,new Id("ip","192.168.2.4"));
        ACL acldigest = new ACL(Perms.READ|Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("xuhui:xh68070204over")));
        List<ACL> acls=new ArrayList<>();
        acls.add(acldigest);
        acls.add(aclip);
        String path = client.create().creatingParentsIfNeeded()
        .withMode(CreateMode.PERSISTENT).withACL(acls).forPath("/node_1/node_1_5","999".getBytes());
        System.out.println(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
