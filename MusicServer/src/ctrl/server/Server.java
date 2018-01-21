package ctrl.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server extends Thread{
	
	private static Server server;
	private ServerSocket serverSocket;
	public Vector<ChatSocket> vector=new Vector<ChatSocket>();
	
	public static Server getS() {
		if (server==null) {
			server=new Server();
			server.start();
		}
		return server;
	}
	private Server() {
	}
	@Override
	public void run() {
		try {
			serverSocket=new ServerSocket(8888);
			while (true) {
				Socket socket=serverSocket.accept();
				ChatSocket chatSocket=new ChatSocket(socket);
				chatSocket.start();
				synchronized (ChatSocket.class) {
					addSocket(chatSocket);
				}
				System.out.println("客户端连接到了本机的8888端口。");
				System.out.println("当前客户端连接数："+vector.size());
			}
		} catch (IOException e) {
			System.out.println("客户端断开了。。。。。。。。。。。。到了本机的8888端口。");
			e.printStackTrace();
		}
	}
	/**
	 * 添加socket线程
	 * @param socket
	 */
	public void addSocket(ChatSocket socket){
		vector.add(socket);
	}
	/**
	 * 删除socket线程
	 * @param socket
	 */
	public void deleteSocket(ChatSocket socket){
		vector.remove(socket);
		System.out.println("当前客户端连接数："+vector.size());
	}
}
