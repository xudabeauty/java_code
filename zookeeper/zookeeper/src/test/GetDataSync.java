package test;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class GetDataSync implements Watcher {

    private static ZooKeeper zooKeeper;
    private static Stat      stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper("192.168.2.4:2181", 5000, new GetDataSync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething() {
        zooKeeper.addAuthInfo("digest", "xuhui:xh68070204over".getBytes());
        try {
            System.out.println(new String(zooKeeper.getData("/node_1", true, stat)));
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                doSomething();
            } else {
                if (event.getType() == EventType.NodeDataChanged) {
                    try {
                        System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
                        System.out.println("stat:" + stat);
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
