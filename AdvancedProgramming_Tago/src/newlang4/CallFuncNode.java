package newlang4;

public class CallFuncNode extends Node{	
	String func_name;
	Node expr_list;
	
	
	public CallFuncNode(Environment env, String func_name){
		super.env = env;
		super.type = NodeType.FUNCTION_CALL;
		this.func_name = func_name;
	}
	
	@Override
	public Value getValue(){
				
		return env.library.get(func_name).invoke((ExprListNode)expr_list);
		
	}
}
