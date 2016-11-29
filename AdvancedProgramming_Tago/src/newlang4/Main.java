package newlang4;

import java.io.FileInputStream;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	       FileInputStream fin = null;
	        LexicalAnalyzer lex;
	        LexicalUnit		first;
	        Environment		env;
	        Node			expression;
	  
	        System.out.println("basic parser");
	        try {
	            fin = new FileInputStream("test.txt");
	        }
	        catch(Exception e) {
	            System.out.println("file not found");
	            System.exit(-1);
	        }
	        lex = new LexicalAnalyzerImpl(new InputString(fin));
	        env = new Environment(lex);
	        first = lex.get();
	        
	        expression = Expr.isMatch(env, first);
	        if (expression != null && expression.Parse()) {
	        	System.out.println(expression);
	        	System.out.println("value = " + expression.getValue());
	        }
	        else System.out.println("syntax error");
	}

}
