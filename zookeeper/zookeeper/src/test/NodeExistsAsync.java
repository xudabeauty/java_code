package test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class NodeExistsAsync implements Watcher {
    private static ZooKeeper zooKeeper;
    public static void main(String[] args) throws Exception {
        zooKeeper=new ZooKeeper("192.168.2.4:2181",5000,new NodeExistsAsync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomethong(ZooKeeper zooKeeper)  {
        zooKeeper.exists("/node_1", true, new IStateCallback(), null);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                doSomethong(zooKeeper);
            }else {
                try {
                    if(event.getType()==EventType.NodeCreated) {
                        System.out.println(event.getPath()+" created");
                        zooKeeper.exists(event.getPath(), true,new IStateCallback(),null);
                    }else if(event.getType()==EventType.NodeDataChanged) {
                        System.out.println(event.getPath()+" updated");
                        zooKeeper.exists(event.getPath(),true,new IStateCallback(), null);
                    }else if(event.getType()==EventType.NodeDeleted) {
                        System.out.println(event.getPath()+" deleted");
                        zooKeeper.exists(event.getPath(), true, new IStateCallback(), null);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

    static class IStateCallback implements AsyncCallback.StatCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            System.out.println("rc:" + rc);
        }
    }

}
