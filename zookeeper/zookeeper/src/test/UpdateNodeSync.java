package test;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class UpdateNodeSync implements Watcher{
    private static ZooKeeper zooKeeper;
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("192.168.2.4:2181",5000,new UpdateNodeSync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    private void doSomething() {
        Stat stat;
        try {
            stat = zooKeeper.setData("/node_1", "123".getBytes(), -1);
            System.out.println("stat:"+stat);
        } catch (KeeperException | InterruptedException e) {
          e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                doSomething();
            }
        }
    }
}
