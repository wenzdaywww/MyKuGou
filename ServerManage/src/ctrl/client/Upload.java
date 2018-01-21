package ctrl.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import model.JSONMsg;
import util.Const;
import view.view.MainFrame;

public class Upload extends Thread {

	private File musicFile;
	private String fileName;
	private long fileLenth;

	public Upload(String filePath) {
		musicFile=new File(filePath);
		Const.canUpload=false;
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
			MainFrame.getMF().getLblUploadState().setText("正在上传音乐文件“ "+fileName+" ”，请稍等。。。");
			byte dataBuf[]=new byte[1024];
			while (true) {
				int readLenth=input.read(dataBuf);
				if (readLenth<0) {
					MainFrame.getMF().getLblUploadState().setText("音乐文件“ "+fileName+" ”，上传成功！");
					Const.canUpload=true;
					break;
				}
				JSONMsg jMsg=new JSONMsg(JSONMsg.UPLOAD, fileName,fileLenth, readLenth);
				Client.getCL().uploadMusicFile(jMsg.toMsg(), dataBuf, 0, readLenth);
			}
			input.close();
		} catch (IOException e) {
			MainFrame.getMF().getLblUploadState().setText("音乐文件“ "+fileName+" ”，上传异常");
			e.printStackTrace();
		}

	}
}
