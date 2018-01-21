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
				System.out.println("�ͻ������ӵ��˱�����8888�˿ڡ�");
				System.out.println("��ǰ�ͻ�����������"+vector.size());
			}
		} catch (IOException e) {
			System.out.println("�ͻ��˶Ͽ��ˡ��������������������������˱�����8888�˿ڡ�");
			e.printStackTrace();
		}
	}
	/**
	 * ���socket�߳�
	 * @param socket
	 */
	public void addSocket(ChatSocket socket){
		vector.add(socket);
	}
	/**
	 * ɾ��socket�߳�
	 * @param socket
	 */
	public void deleteSocket(ChatSocket socket){
		vector.remove(socket);
		System.out.println("��ǰ�ͻ�����������"+vector.size());
	}
}
