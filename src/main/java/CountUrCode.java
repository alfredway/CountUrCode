import java.util.Scanner;

import alfred.CountCode;
import alfred.CountStarter;


public class CountUrCode {
	public static void printMsg(){
		System.out.println("*********************************************************");
		System.out.println("欢迎使用代码行数统计器");
		System.out.println("使用前请配置好config文件");
		System.out.println("1.开始");
		System.out.println("2.结束并统计");
		System.out.println("0.退出");
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
					System.out.println("代码情况已经复制到剪贴板！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
			}else{
				System.out.println("您只能输入0,1,2的命令");
			}
			printMsg();
			order = scan.nextInt();
		}
	}

}
