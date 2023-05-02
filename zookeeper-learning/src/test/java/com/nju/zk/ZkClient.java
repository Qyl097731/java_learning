package com.nju.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * @description 客户端测试
 * @date 2023/5/2 18:12
 * @author: qyl
 */
public class ZkClient {
    private static String connectString = "master:2181";
    //连接时长可以设置长一点避免连接失败
    private static int sessionTimeout = 40000;
    private ZooKeeper zkClient = null;

    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper (connectString, sessionTimeout, new Watcher () {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 收到事件通知后的回调函数（用户的业务逻辑）
                System.out.println (watchedEvent.getType () + "--" + watchedEvent.getPath ());
                // 再次启动监听
                try {
                    List<String> children = zkClient.getChildren ("/", true);
                    for (String child : children) {
                        System.out.println (child);
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        });
    }

    // 创建子节点
    @Test
    public void create() throws Exception {
        // 参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ；参数 4：节点的类型
        String nodeCreated = zkClient.create("/nju","shuaige".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    //获取子节点并监听节点变化
    // 获取子节点
    @Test
    public void getChildren() throws Exception {
        //获取根节点，true是使用客户端的监听，即上述init中的watcher
        List<String> children = zkClient.getChildren("/", true);
        //遍历子节点
        for (String child : children) {
            System.out.println(child);
        }
        //这里意思就是你只要执行zk的api指令，就会走监听器重写的方法，最后加一个延迟，主线程睡眠但是监听器还在
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    // 判断 znode 是否存在
    @Test
    public void exist() throws Exception {
        Stat stat = zkClient.exists("/nju", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }
}
