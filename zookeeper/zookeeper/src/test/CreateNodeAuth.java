package test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class CreateNodeAuth implements Watcher {
    private static ZooKeeper zooKeeper;
    private static boolean somethingDone=false;
    public static void main(String[] args)  throws Exception{
        zooKeeper=new ZooKeeper("192.168.2.4:2181",5000,new CreateNodeAuth());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    /*
     * 权限模式(scheme):ip,digest
     * 授权对象(ID)
     * ip权限模式：具体的ip地址
     * digest权限模式：username:Base64(SHA-1(username:password))
     * 权限(permission):create(C),DELETE(D) READ(R),WRIETE(W),ADMIN(A)
     * 注:单个权限，完全权限，复合权限
     * 权限组合：scheme+ID+permission
     */
    private void doSomething() throws NoSuchAlgorithmException {
        ACL acllp=new ACL(Perms.READ,new Id("ip","192.168.2.4"));
        ACL adlDigest=new ACL(Perms.READ|Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("xuhui:xh68070204over")));
        List<ACL> acls=new ArrayList<>();
        acls.add(adlDigest);
        acls.add(acllp);
        try {
            String path = zooKeeper.create("/node_6", "123".getBytes(), acls, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
           e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("return path:"+"path");
        somethingDone=true;
        
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件"+event);
        if(event.getState()==KeeperState.SyncConnected) {
            if(!somethingDone&&event.getType()==EventType.None&&event.getPath()==null) {
                try {
                    doSomething();
                } catch (NoSuchAlgorithmException e) {
                  e.printStackTrace();
                }
            }
        }
    }
    

}
