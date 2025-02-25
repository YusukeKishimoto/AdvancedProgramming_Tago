package newlang4;

import java.util.Hashtable;

public class Environment {
	   LexicalAnalyzer input;
	   Hashtable<String, Function> library;
	   Hashtable<String, Node> var_table;
	    
	    public Environment(LexicalAnalyzer my_input) {
	        input = my_input;
	        library = new Hashtable<String, Function>();
	        library.put("PRINT", new PrintFunction() );
	        library.put("SQRT", new SquareRootFunction());
	        library.put("MOD", new ModuloFunction());
	        var_table = new Hashtable<String, Node>();
	    }
	    
	    public LexicalAnalyzer getInput() {
	        return input;
	    }
	    public Function getFunction(String fname) {
	        return (Function) library.get(fname);
	    }
	    
	    public VariableNode getVariable(String vname) {
	        VariableNode v;
	        v = (VariableNode) var_table.get(vname);
	        if (v == null) {
	            v = new VariableNode(vname);
	            var_table.put(vname, v);
	        }
	        return v;
	    }
}
