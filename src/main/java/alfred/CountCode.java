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
	
	//�����ļ���
	public static String dirPath = null;
	//����ƥ���ļ���
	public static String regEx = null;
	public static Pattern p = null;
	
	//����ͳ�ƵĴ��룬����import��ͷ�Ĵ��벻����
	public static String ignoreCode = null;
	
	public static Pattern pIgnore = null;
	
	//������ʱ��,��λΪ����
//	public static int totalTime = 0;
	public static long startTime = 0;
	//�����ļ���ͳ������
	public static StringBuilder sb = new StringBuilder();
	
	//�ļ��в㼶
	private static int dirLevel = 0;
	
	//�Ƿ�ʹ��ʱ�����ж��ļ�����ͳ��
	private static boolean useTime = false;
	
	//��ʼ������
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
			throw new Exception("!!!!!�����ļ������⣡");
		}finally{
			if(propIs!=null)
				propIs.close();
		}
		
	}
	
	public static void countAllLines() throws Exception{
		if(!hasInit)
			init();
		
		if(dirPath==null||dirPath.trim().equals("")){
			throw new Exception("�ļ������Ʋ���Ϊ�գ�");
		}
		File dir = new File(dirPath);
		//�ļ��в�����ʱ�׳��쳣���Ա�ǰ̨����
		if(!dir.exists())
			throw new FileNotFoundException("��ָ�����ļ��С�"+dirPath+"�������ڣ�������ָ����");
		
		//����������ʽ��ȷ������
		if(regEx != null && !regEx.trim().equals("")){
			p = Pattern.compile(regEx);
		}
		
		if(ignoreCode != null && !ignoreCode.trim().equals("")){
			pIgnore = Pattern.compile(ignoreCode);
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		
		sb.append("["+sdf.format(new Date())+"]����ͳ��������£�\n");
		//��ȡ��ʼʱ��
		File tmpF = new File("tmp");
		if(!tmpF.exists()){
			System.out.println("��ʼʱ��û�м�¼��ʱ���޷�ͳ��!");
		}else{
		BufferedReader br = new BufferedReader(new FileReader(tmpF));
		startTime = Long.valueOf(br.readLine());
		br.close();
		//ɾ����ʱ�ļ�
		tmpF.delete();
//		System.out.println(tmpF.getAbsolutePath());
		sb.append("��ʼʱ��Ϊ:"+timeFormat.format(new Date(startTime))+"\n")
		  .append("����ʱ��Ϊ��"+timeFormat.format(new Date())+"\n")
		  .append("�ܹ��ķ���["+(System.currentTimeMillis()-startTime)/60000+"]����"+"\n")
		;
		}
		sb.append("��������������£�(��Ч������ע������������������������)\n");
		//�����ļ�
		if(dir.isFile() && Pattern.matches(regEx, dir.getName())){
			processFile(dir);
		}else{
		//�����ļ��У�ͳ��ÿ��ƥ��regEx���ļ�������
		countDir(dir);
		}
		
		sb.append("-------------------------------\n")
		  .append("����Ч����Ϊ��"+codeLines+"\n")
		  .append("��ע������Ϊ��"+commentLines+"\n")
		  .append("�ܿ�����Ϊ��"+blankLines+"\n")
		  .append("�ܺ�������Ϊ��"+ignoreLines+"\n")
		  .append("-------------------------------\n")
		;
		
		if(codeLines < 100){
			sb.append("����������õ��������𣿣���100�ж�����������");
		}else if(codeLines<200){
			sb.append("������������㣡");
		}else if(codeLines<300){
			sb.append("�����Ѿ���Ŭ���ˣ���������!");
		}else{
			sb.append("ţ�ư�������Ŭ�����������������飬��������ģ�");
		}
		
		sb.append("\n*************************************************");
		
		//��sb������д��ϵͳ������
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(new StringSelection(sb.toString()), null);
	}

	/**
	 * �����ļ���
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
				//�ļ�����ƥ��
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
	 * ������ļ��ڵĴ�����Ŀ
	 * @param dir
	 */
	private static void processFile(File file) {
		int cLine = 0,bLine = 0,comLine = 0,igLine = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while(line != null){
				line = line.trim();
				//���Բ��ƵĴ����У���import��ͷ��
				if(pIgnore != null && pIgnore.matcher(line).matches()){
					igLine++;
					line = reader.readLine();
					continue;
				}
				//��¼����
				if(line.equals("")){
					bLine++;
				}else if(line.startsWith("//")){
					//��¼��б��ע��
					comLine++;
				}else if(line.startsWith("/*")){
					//�Ǻ�ע�͵ļ�¼
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
			
			//��¼������
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
