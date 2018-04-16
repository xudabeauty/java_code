package com.my.ZookeeperClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class WriteData {

	public static void main(String[] args) {
		ZkClient zc = new ZkClient("192.168.2.4:2181",10000,10000,new SerializableSerializer());
		System.out.println("conneted ok!");
		
		
		User u = new User();
		u.setId(2);
		u.setName("test2");
		zc.writeData("/node_1/node_1_1", u, 1);
		
	}
	
}
