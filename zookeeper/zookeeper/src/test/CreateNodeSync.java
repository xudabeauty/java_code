package test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateNodeSync implements Watcher {
    private static ZooKeeper zookeeper;
    public static void main(String[] args) throws Exception {
        zookeeper =new ZooKeeper("192.168.2.4:2181",5000,new CreateNodeSync());
        System.out.println(zookeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
        
    }
    private void doSomething() {
        try {
            String path = zookeeper.create("/node_5", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("return path:"+path);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do Something");
        
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件"+event);
        if(event.getState()==KeeperState.SyncConnected) {
            if (event.getType()==EventType.None&&null==event.getPath()) {
                doSomething();
            }
        }
    }

}
