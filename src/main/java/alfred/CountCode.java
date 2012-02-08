package alfred;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;


public class CountCode {
	
	public static int codeLines = 0, blankLines = 0, commentLines = 0,ignoreLines = 0;
	private static boolean hasInit = false;
	
	//代码文件夹
	public static String dirPath = null;
	//正则匹配文件名
	public static String regEx = null;
	public static Pattern p = null;
	
	//忽略统计的代码，如以import开头的代码不计入
	public static String ignoreCode = null;
	
	public static Pattern pIgnore = null;
	
	//消耗总时间,单位为分钟
//	public static int totalTime = 0;
	public static long startTime = 0;
	//所有文件的统计数据
	public static StringBuilder sb = new StringBuilder();
	
	//文件夹层级
	private static int dirLevel = 0;
	
	//是否使用时间来判断文件纳入统计
	private static boolean useTime = false;
	
	//初始化代码
	public static void init() throws Exception{
		Properties prop = new Properties();
		InputStream propIs = null;
		try {
			propIs = CountCode.class.getClassLoader().getResourceAsStream("config");
			if(propIs == null){
				propIs = new FileInputStream("config");
			}
			prop.load(propIs);
			/*if(!prop.getProperty("dirPath").equals(""))
				dirPath = prop.getProperty("dirPath");*/
			if(!prop.getProperty("regEx").equals(""))
				regEx = prop.getProperty("regEx");
			if(!prop.getProperty("ignoreCode").equals(""))
				ignoreCode = prop.getProperty("ignoreCode");
			if(prop.getProperty("useTime").equals("true")){
				useTime = true;
			}else{
				useTime = false;
			}
			
			hasInit=true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw e;
		} catch (NullPointerException e){
			throw new Exception("!!!!!配置文件有问题！");
		}finally{
			if(propIs!=null)
				propIs.close();
		}
		
	}
	
	public static void countAllLines() throws Exception{
		if(!hasInit)
			init();
		
		if(dirPath==null||dirPath.trim().equals("")){
			throw new Exception("文件夹名称不能为空！");
		}
		File dir = new File(dirPath);
		//文件夹不存在时抛出异常，以便前台处理
		if(!dir.exists())
			throw new FileNotFoundException("您指定的文件夹【"+dirPath+"】不存在，请重新指定！");
		
		//构造正则表达式，确定无误
		if(regEx != null && !regEx.trim().equals("")){
			p = Pattern.compile(regEx);
		}
		
		if(ignoreCode != null && !ignoreCode.trim().equals("")){
			pIgnore = Pattern.compile(ignoreCode);
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		
		sb.append("["+sdf.format(new Date())+"]代码统计情况如下：\n");
		//读取开始时间
		File tmpF = new File("tmp");
		if(!tmpF.exists()){
			System.out.println("开始时间没有记录，时间无法统计!");
		}else{
		BufferedReader br = new BufferedReader(new FileReader(tmpF));
		startTime = Long.valueOf(br.readLine());
		br.close();
		//删除临时文件
		tmpF.delete();
//		System.out.println(tmpF.getAbsolutePath());
		sb.append("开始时间为:"+timeFormat.format(new Date(startTime))+"\n")
		  .append("结束时间为："+timeFormat.format(new Date())+"\n")
		  .append("总共耗费了["+(System.currentTimeMillis()-startTime)/60000+"]分钟"+"\n")
		;
		}
		sb.append("新增代码情况如下：(有效行数，注释行数，空行数，忽略行数)\n");
		//单个文件
		if(dir.isFile() && Pattern.matches(regEx, dir.getName())){
			processFile(dir);
		}else{
		//遍历文件夹，统计每个匹配regEx的文件的行数
		countDir(dir);
		}
		
		sb.append("-------------------------------\n")
		  .append("总有效行数为："+codeLines+"\n")
		  .append("总注释行书为："+commentLines+"\n")
		  .append("总空行数为："+blankLines+"\n")
		  .append("总忽略行数为："+ignoreLines+"\n")
		  .append("-------------------------------\n")
		;
		
		if(codeLines < 100){
			sb.append("你个懒鬼，多敲点代码会死吗？！连100行都不到！！！");
		}else if(codeLines<200){
			sb.append("马马虎虎，还差点！");
		}else if(codeLines<300){
			sb.append("恩，已经很努力了，继续加油!");
		}else{
			sb.append("牛逼啊，这样努力哪有做不到的事情，你是最棒的！");
		}
		
		sb.append("\n*************************************************");
		
		//将sb的内容写入系统剪贴板
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(new StringSelection(sb.toString()), null);
	}

	/**
	 * 遍历文件夹
	 * @param dir
	 */
	private static void countDir(File dir) {
		int n=dirLevel;
		while(n-->1){
			sb.append("    ");
		}
		if(dirLevel == 0){
			sb.append(dir.getAbsolutePath());
		}else{
			sb.append("|___"+dir.getName());
		}
		sb.append("\n");
		File[] files = dir.listFiles();
		for(File f : files){
			if(f.isDirectory()){
				dirLevel++;
				countDir(f);
				dirLevel--;
			}else{
				//文件名不匹配
				if(p!=null&&!p.matcher(f.getName()).matches()){
					continue;
				}
				if(useTime&&f.lastModified()<startTime){
					continue;
				}
				dirLevel++;
				processFile(f);
				dirLevel--;
			}
			
		}
	}

	/**
	 * 计算该文件内的代码数目
	 * @param dir
	 */
	private static void processFile(File file) {
		int cLine = 0,bLine = 0,comLine = 0,igLine = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while(line != null){
				line = line.trim();
				//忽略不计的代码行，如import开头的
				if(pIgnore != null && pIgnore.matcher(line).matches()){
					igLine++;
					line = reader.readLine();
					continue;
				}
				//记录空行
				if(line.equals("")){
					bLine++;
				}else if(line.startsWith("//")){
					//记录反斜杠注释
					comLine++;
				}else if(line.startsWith("/*")){
					//星号注释的记录
					comLine++;
					if(line.indexOf("*/") == -1){
						while(line.indexOf("*/") == -1){
							comLine++;
							line = reader.readLine();
						}
					}
				}else{
					cLine++;
				}
				
				line = reader.readLine();
			}
			
			//记录总行数
			codeLines += cLine;
			blankLines += bLine;
			commentLines += comLine;
			ignoreLines += igLine;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int n=dirLevel;
		while(n-->1){
			sb.append("    ");
		}
		if(dirLevel > 0){
			sb.append("|___");
		}
			sb.append(file.getName()+"["+cLine+","+comLine+","+bLine+","+igLine+"]\n");
		
	}
	
}
