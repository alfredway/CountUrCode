package alfred;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CountStarter {
	
	public static void run(){
		System.out.println("��ϲ������Ҫ�����ˣ��������ڵ�ʲô���Ͻ����ְɣ�");
		long startTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("��ʼʱ�䣺"+sdf.format(new Date(startTime)));
		System.out.println("��νţ�˾�������˵��ʱ�����ڹ�˼�������ʱ�����Ѿ������ˣ���Զ������̫��");
		System.out.println("Ϊ�߳��ɣ����߳�����");
		System.out.println("�����ƾ裡���Ĳ��������ҳ��������Ի�����ȻҪ����");
		File f = new File("tmp");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(String.valueOf(startTime));
			bw.flush();
			bw.close();
			
			
//			BufferedReader br = new BufferedReader(new FileReader(f));
//			long st = Long.valueOf(br.readLine());
//			System.out.println(st+"elapsed: "+(System.currentTimeMillis()-st));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		
	}

}
