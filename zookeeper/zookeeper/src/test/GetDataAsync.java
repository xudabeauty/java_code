package test;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class GetDataAsync implements Watcher {
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper("192.168.2.4:2181", 5000, new GetDataAsync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    private void doSomething() {
        zooKeeper.getData("/node_1", true, new IDataCallback(),null);
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                doSomething();
            }else {
                if(event.getType()==EventType.NodeDataChanged) {
                    zooKeeper.getData(event.getPath(), true, new IDataCallback(),null);
                }
            }
        }
    }
    
    static class IDataCallback implements AsyncCallback.DataCallback{

        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            try {
                System.out.println(new String(zooKeeper.getData(path, true, stat)));
            } catch (KeeperException | InterruptedException e) {
              e.printStackTrace();
            }
            System.out.println("stat:"+stat);
        }
        
    }
}
