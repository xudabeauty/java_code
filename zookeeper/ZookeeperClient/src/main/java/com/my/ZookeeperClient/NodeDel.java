package com.my.ZookeeperClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class NodeDel {

	public static void main(String[] args) {
		ZkClient zc = new ZkClient("192.168.2.9:2181",10000,10000,new SerializableSerializer());
		System.out.println("conneted ok!");
		
		boolean e = zc.exists("/node_4");
		
		System.out.println(e);
		
	}
	
}
