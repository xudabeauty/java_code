package test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class DeleteNodeAsync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.2.4:2181", 5000, new DeleteNodeAsync());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething() {
        zooKeeper.delete("/node_5", -1,new IVoidCallback(),null);
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==KeeperState.SyncConnected) {
            if(event.getType()==EventType.None&&null==event.getPath()) {
                doSomething();
            }
        }
    }

    static class IVoidCallback implements AsyncCallback.VoidCallback {

        @Override
        public void processResult(int rc, String path, Object ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path" + path).append("\n");
            sb.append("ctx=" + ctx).append("\n");
            System.out.println(sb.toString());
        }
    }
}
