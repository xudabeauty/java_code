package test;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class GetChildrenSync implements Watcher {
    
    private static ZooKeeper zooKeeper;
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
                
        zooKeeper = new ZooKeeper("192.168.2.4:2181",5000,new GetChildrenSync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    private void doSomething() {
        List<String> children;
        try {
            children = zooKeeper.getChildren("/", true);
            System.out.println(children);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                doSomething();
            }else {
                if(event.getType()==EventType.NodeChildrenChanged) {
                    try {
                        System.out.println(zooKeeper.getChildren(event.getPath(), true));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                    }
                }
                
            }
        }
    }

}
