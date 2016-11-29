package newlang3;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InputStream is = new FileInputStream("SourceProg.bas");
		LexicalAnalyzer lex = new LexicalAnalyzerImpl(is);
		
		while(true){
			LexicalUnit unit = lex.get();
			System.out.println(unit);
			if(unit.getType() == LexicalType.EOF) break;
		}

	}

}
