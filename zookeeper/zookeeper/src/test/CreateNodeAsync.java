package test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateNodeAsync implements Watcher {
    private static ZooKeeper zookeeper;
    public static void main(String[] args) throws Exception {
        zookeeper=new ZooKeeper("192.168.2.4:2181",5000,new CreateNodeAsync());
        System.out.println(zookeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }
    private void dosomething() {
        zookeeper.create("/node_6", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,new IStringCallback(),"创建");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件"+event);
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                dosomething();
            }
        }
    }
    
    static class IStringCallback implements AsyncCallback.StringCallback{

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=+rc").append("\n");
            sb.append("rc="+rc).append("\n");
            sb.append("path="+path).append("\n");
            sb.append("ctx="+ctx).append("\n");
            sb.append("name="+name);
            System.out.println(sb.toString());
        }
        
    }

}
