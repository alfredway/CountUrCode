import java.util.Scanner;

import alfred.CountCode;
import alfred.CountStarter;


public class CountUrCode {
	public static void printMsg(){
		System.out.println("*********************************************************");
		System.out.println("��ӭʹ�ô�������ͳ����");
		System.out.println("ʹ��ǰ�����ú�config�ļ�");
		System.out.println("1.��ʼ");
		System.out.println("2.������ͳ��");
		System.out.println("0.�˳�");
		System.out.println("*********************************************************");
	}
	public static void main(String[] args) {
		printMsg();
		Scanner scan = new Scanner(System.in);
		int order = scan.nextInt();
		while(order!=0){
			if(order==1){
				CountStarter.run();
			}else if(order==2){
				try {
					CountCode.countAllLines();
					System.out.println(CountCode.sb);
					System.out.println("��������Ѿ����Ƶ������壡");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
			}else{
				System.out.println("��ֻ������0,1,2������");
			}
			printMsg();
			order = scan.nextInt();
		}
	}

}
