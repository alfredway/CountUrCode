import java.io.File;
import java.util.Scanner;

import alfred.CountCode;
import alfred.CountStarter;


public class CountUrCodeNew {
	
	
	
	
	public static void main(String[] args) {
		System.out.println("��ӭʹ�ô���ͳ������������Դ���ļ��У�");
		Scanner scan = new Scanner(System.in);
		String dirPath = scan.nextLine();
		File f = new File(dirPath);
		while(!f.exists()){
			System.out.println("������ļ��в����ڣ����������룡");
			dirPath = scan.nextLine();
			f = new File(dirPath);
		}
		//��ʼͳ��
		CountStarter.run();
		
		//�趨�ļ���
		CountCode.dirPath = dirPath;
		System.out.println("���������ַ������˴�ͳ�ƣ�");
		scan.nextLine();
		try {
			CountCode.countAllLines();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println(CountCode.sb);
		System.out.println("��������Ѿ����Ƶ������壡");
		scan.nextLine();
		
	}
	

}
