package ctrl.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import model.JSONMsg;

public class Download extends Thread {

	private File musicFile;
	private String fileName;
	private long fileLenth;
	private String type;
	private ChatSocket chatSocket;

	public Download(String type,String filePath,ChatSocket chatSocket) {
		this.type=type;
		this.chatSocket=chatSocket;
		musicFile=new File(filePath);
		fileName=musicFile.getName();
		fileLenth=musicFile.length();
	}

	@Override
	public void run() {
		if (!musicFile.exists()||musicFile.isDirectory()) {
			return;
		}
		try {
			FileInputStream input=new FileInputStream(musicFile);
			System.out.println("开始发送音乐"+fileName+"了。。。。。。。。。。。。");
			byte dataBuf[]=new byte[1024];
			while (true) {
				int readLenth=input.read(dataBuf);
				if (readLenth<0) {
					System.out.println("readLenth="+readLenth+fileName+"音乐发送结束了。。。。。。。。。。。。");
					break;
				}
				if (type.equals(JSONMsg.DOWNLOAD)) {
					JSONMsg jMsg=new JSONMsg(JSONMsg.DOWNLOAD, fileName, fileLenth, readLenth);
					chatSocket.downloadMusic(jMsg.toMsg(), dataBuf, 0, readLenth);
				}else {
					JSONMsg jMsg=new JSONMsg(JSONMsg.LISTENING, fileName, fileLenth, readLenth);
					chatSocket.downloadMusic(jMsg.toMsg(), dataBuf, 0, readLenth);
				}
			}
			input.close();
		} catch (IOException e) {
			System.out.println("文件下载异常了。。。。。。。。。。。。。。。。。。。。。。");
			e.printStackTrace();
		}

	}
}
