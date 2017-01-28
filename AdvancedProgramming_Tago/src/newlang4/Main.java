package newlang4;

import java.io.FileInputStream;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	        FileInputStream fin = null;
	        LexicalAnalyzer lex;
	        //LexicalUnit		first;
	        Environment		env;
	        //Node			expression;
	        Node			prog;
	  
	        System.out.println("basic parser");
	        try {
	            fin = new FileInputStream("SourceProg.bas");
	        }
	        catch(Exception e) {
	            System.out.println("file not found");
	            System.exit(-1);
	        }
	        lex = new LexicalAnalyzerImpl(fin);
	        env = new Environment(lex);
	        /*
	        first = lex.get();
	        
	        expression = ProgramNode.isMatch(env, first);
	        if (expression != null && expression.Parse()) {
	        	System.out.println(expression);
	        	System.out.println("value = " + expression.getValue());
	        }
	        else System.out.println("syntax error");
	        */
	        prog = (Node) new ProgramNode(env);
	        if(prog != null && prog.Parse()){
	        	System.out.println("\n---RESULT---  ");
	        	prog.getValue();
	        }
	        else{
	        	System.out.println("syntax error");
	        }
	}

}
