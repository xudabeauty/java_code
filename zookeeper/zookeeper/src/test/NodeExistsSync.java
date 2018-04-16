package test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class NodeExistsSync implements Watcher{
    private static ZooKeeper zookeeper;
    public static void main(String[] args) throws Exception {
        zookeeper=new ZooKeeper("192.168.2.4:2181",5000,new NodeExistsSync());
        System.out.println("stat is:"+zookeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }
    private void doSomething(ZooKeeper zooKeeper) {
        try {
            Stat stat=zookeeper.exists("/node_1", true);
            System.out.println("doSomething is:"+stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                doSomething(zookeeper);
            }else {
                try {
                    if(event.getType()==EventType.NodeCreated) {
                        System.out.println(event.getPath()+" created");
                        System.out.println(zookeeper.exists(event.getPath(), true));
                    }else if (event.getType()==EventType.NodeDataChanged) {
                        System.out.println(event.getPath()+" updated");
                        System.out.println(zookeeper.exists(event.getPath(), true));
                    }else if (event.getType()==EventType.NodeDeleted) {
                        System.out.println(event.getPath()+" deleted");
                        System.out.println(zookeeper.exists(event.getPath(), true));
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

}
