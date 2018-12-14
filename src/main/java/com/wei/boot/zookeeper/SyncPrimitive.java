package com.wei.boot.zookeeper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SyncPrimitive implements Watcher{

	static ZooKeeper zk = null;
	static Integer mutex;
	String root;
	
	SyncPrimitive(String address) {
		if(zk == null) {
			try {
				System.out.println("开始初始化zk...");
				zk = new ZooKeeper(address, 3000, this);
				mutex = new Integer(-1);
			} catch (IOException e) {
				System.out.println("创建zk实例失败");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public synchronized void process(WatchedEvent event) {
		synchronized(mutex) {
			System.out.println("Processing: " + event.getType());
			mutex.notify();
		}
	}

	/**
	 * Barrier
	 * @author weisihua
	 * 2018年8月24日 下午2:23:24
	 */
	public static class Barrier extends SyncPrimitive {

		int size;
		String name;
		
		Barrier(String address, String root, int size) {
			super(address);
			this.root = root;
			this.size = size;
			
			if(zk != null) {
				try {
					Stat s = zk.exists(root, false);
					if(s == null) {
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// My node name
			try {
				name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		/**
		 * join Barrier
		 * @return
		 * @throws KeeperException
		 * @throws InterruptedException
		 */
		boolean enter() throws KeeperException, InterruptedException {
			zk.create(root + "/" + name, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			while(true) {
				synchronized (mutex) {
					List<String> list = zk.getChildren(root, true);
					if(list.size() < size) {
						mutex.wait();
					}
					else {
						return true;
					}
				}
			}
		}
		
		/**
		 * Wait until all reach barrier
		 * @return
		 * @throws InterruptedException
		 * @throws KeeperException
		 */
		boolean leave() throws InterruptedException, KeeperException {
			zk.delete(root + "/" + name, 0);
			while(true) {
				synchronized (mutex) {
					List<String> list = zk.getChildren(root, true);
					if(!list.isEmpty()) {
						mutex.wait();
					}
					else {
						return true;
					}
				}
			}
		}
	}
	
	public static class Queue extends SyncPrimitive {

		Queue(String address, String name) throws IOException {
			super(address);
			this.root = name;
			
			if(zk != null) {
				try {
					Stat s = zk.exists(root, false);
					if(s == null) {
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		boolean produce(int i) throws KeeperException, InterruptedException {
			ByteBuffer b = ByteBuffer.allocate(4);
			byte[] value = null;
			b.putInt(i);
			value = b.array();
			zk.create(root+"/element", value, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			return true;
		}
		
		/**
		 * remove first element from queue
		 * @return
		 * @throws InterruptedException 
		 * @throws KeeperException 
		 */
		int consume() throws KeeperException, InterruptedException {
			Stat stat = null;
			while(true) {
				synchronized (mutex) {
					List<String> list = zk.getChildren(root, true);
					if(!list.isEmpty()) {
						mutex.wait();
					}
					else {
						int min = Integer.parseInt(list.get(0).substring(7));
						String minNode = list.get(0);
						for(String s : list) {
							Integer tempValue = new Integer(s.substring(7));
							if(min > tempValue) {
								min = tempValue;
								minNode = s;
							}
						}
						System.out.println("Temporary value: " + root + "/" + minNode);
						byte[] b = zk.getData(root+"/"+minNode, false, stat);
						zk.delete(root+"/"+minNode, 0);
						ByteBuffer bf = ByteBuffer.wrap(b);
						return bf.getInt();
					}
				}
			}
		}
		
	}
}
