package newlang4;

public class VariableNode extends Node {
	String var_name;
	//Value var;

	/** Creates a new instance of variable */
	public VariableNode(String name) {
		var_name = name;
		super.type = NodeType.VARIABLE;
	}
	public VariableNode(LexicalUnit lu) {
		var_name = lu.getValue().getSValue();
		super.type = NodeType.VARIABLE;
	}

	public static Node isMatch(Environment my_env, LexicalUnit first) {
		if (first.getType() == LexicalType.NAME) {
			VariableNode v;

			String vname = first.getValue().getSValue();
			v = my_env.getVariable(vname);
			return v;

			//return new VariableNode(first.getValue().getSValue());
		}
		return null;
	}

	public void setValue(Value my_val) {
		val = my_val;
		//System.out.println(val.getIValue());
		//env.var_table.put("var_name", var);
	}

	public Value getValue() {
		return val;
	}

}
