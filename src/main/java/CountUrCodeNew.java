import java.io.File;
import java.util.Scanner;

import alfred.CountCode;
import alfred.CountStarter;


public class CountUrCodeNew {
	
	
	
	
	public static void main(String[] args) {
		System.out.println("欢迎使用代码统计器，请输入源码文件夹：");
		Scanner scan = new Scanner(System.in);
		String dirPath = scan.nextLine();
		File f = new File(dirPath);
		while(!f.exists()){
			System.out.println("输入的文件夹不存在，请重新输入！");
			dirPath = scan.nextLine();
			f = new File(dirPath);
		}
		//开始统计
		CountStarter.run();
		
		//设定文件夹
		CountCode.dirPath = dirPath;
		System.out.println("输入任意字符结束此次统计！");
		scan.nextLine();
		try {
			CountCode.countAllLines();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println(CountCode.sb);
		System.out.println("代码情况已经复制到剪贴板！");
		scan.nextLine();
		
	}
	

}
