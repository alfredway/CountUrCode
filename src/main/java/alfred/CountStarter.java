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
		System.out.println("恭喜，你又要进步了！！！还在等什么，赶紧动手吧！");
		long startTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("开始时间："+sdf.format(new Date(startTime)));
		System.out.println("所谓牛人就是你在说的时候他在构思，你想的时候他已经动手了！永远都不会太晚！");
		System.out.println("为者常成，行者常至！");
		System.out.println("功不唐捐！无心插柳，尚且成荫；有心栽花，当然要发！");
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
