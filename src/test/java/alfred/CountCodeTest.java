package alfred;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class CountCodeTest {

	@Test
	public void test() {
//		CountCode.dirPath = "F:/workspace/spring/countCodeLines/src/main/java/";
//		CountCode.regEx = ".*\\.java";
//		CountCode.ignoreCode = "package.*";
		try {
			CountCode.countAllLines();
//			System.out.println("����������Ϊ��"+CountCode.codeLines);
//			System.out.println("������Ϊ��"+CountCode.blankLines);
//			System.out.println("ע������Ϊ��"+CountCode.commentLines);
//			System.out.println("��������Ϊ��"+CountCode.ignoreLines);
			System.out.println(CountCode.sb);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
