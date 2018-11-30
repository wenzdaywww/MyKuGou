package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class DataUtil {
	
//	public static void main(String[] args) {
//		DataUtil.saveData("Temp/", "Music/", 3,2);
//		System.out.println(DataUtil.getTempPath());
//		System.out.println(DataUtil.getDownloadPath());
//		System.out.println(DataUtil.getPlayStyle());
//		System.out.println(DataUtil.getDownloadNum());
//	}
	/**
	 * ��������
	 * @param tempPath
	 * @param downloadPath
	 * @param playStyle
	 */
	public static void saveData(String tempPath,String downloadPath,int playStyle,int downloadNum){
		try {
			FileOutputStream fileOut=new FileOutputStream("lib/data.dat");
			DataOutputStream output=new DataOutputStream(fileOut);
			output.writeUTF(tempPath);
			output.writeUTF(downloadPath);
			output.writeInt(playStyle);
			output.writeInt(downloadNum);
			output.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ����·��
	 * @param fileName
	 * @return
	 */
	public static String getTempPath(){
		String tempPath="Temp/";
		try {
			FileInputStream fileIn=new FileInputStream("lib/data.dat");
			DataInputStream input=new DataInputStream(fileIn);
			tempPath=input.readUTF();
			input.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempPath;
	}
	/**
	 * ����·��
	 * @param fileName
	 * @return
	 */
	public static String getDownloadPath(){
		String downloadPath="Music/";
		try {
			FileInputStream fileIn=new FileInputStream("lib/data.dat");
			DataInputStream input=new DataInputStream(fileIn);
			input.readUTF();
			downloadPath=input.readUTF();
			input.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return downloadPath;
	}
	/**
	 * ����ģʽ
	 * @param fileName
	 * @return
	 */
	public static int getPlayStyle(){
		int playStyle=3;
		try {
			FileInputStream fileIn=new FileInputStream("lib/data.dat");
			DataInputStream input=new DataInputStream(fileIn);
			input.readUTF();
			input.readUTF();
			playStyle=input.readInt();
			input.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return playStyle;
	}
	/**
	 * ���ظ���
	 * @return
	 */
	public static int getDownloadNum(){
		int downloadNum=2;
		try {
			FileInputStream fileIn=new FileInputStream("lib/data.dat");
			DataInputStream input=new DataInputStream(fileIn);
			input.readUTF();
			input.readUTF();
			input.readInt();
			downloadNum=input.readInt();
			input.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return downloadNum;
	}
}
