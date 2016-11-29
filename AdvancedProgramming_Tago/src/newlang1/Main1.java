package newlang1;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InputStream in = new FileInputStream("SourceProg.bas");
		Reader r =  new InputStreamReader(in);

		while(true){
			int ci = r.read() ;
			if(ci == -1) break;
			char c = (char)ci;
			
			System.out.print(c);
		}		
		r.close();
	}

}
