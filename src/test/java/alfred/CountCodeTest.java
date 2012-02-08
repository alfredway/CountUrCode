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
//			System.out.println("代码总行数为："+CountCode.codeLines);
//			System.out.println("空行数为："+CountCode.blankLines);
//			System.out.println("注释行数为："+CountCode.commentLines);
//			System.out.println("忽略行数为："+CountCode.ignoreLines);
			System.out.println(CountCode.sb);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
