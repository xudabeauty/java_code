package test;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class CreateSession implements Watcher{
    private static ZooKeeper zooKeeper;
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper=new ZooKeeper("192.168.2.9:2181", 5000, new CreateSession());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }
    
   private void doSomething() {
       System.out.println("doSomething");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件"+event);
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                doSomething();
            }
        }
    }

}
