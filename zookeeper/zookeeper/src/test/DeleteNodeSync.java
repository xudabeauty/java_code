package test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class DeleteNodeSync implements Watcher {
    private static ZooKeeper zooKeeper;
    public static void main(String[] args) throws Exception {
        zooKeeper=new ZooKeeper("192.168.2.4:2181",5000, new DeleteNodeSync());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    private void doSomething() {
        try {
            zooKeeper.delete("/node_4", -1);
        } catch (Exception e) {
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
