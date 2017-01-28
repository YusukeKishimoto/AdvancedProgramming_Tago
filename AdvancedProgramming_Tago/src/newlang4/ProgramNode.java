package newlang4;

public class ProgramNode extends Node{
	Environment env;
	Node stmt_list;
		
	public ProgramNode(Environment env) {
		this.env = env;
		this.type = NodeType.PROGRAM;
		// TODO Auto-generated constructor stub
	}
	//条件付きのnew 自分がファースト集合にマッチしたかどうかを考える?
	public static Node isMatch(Environment env, LexicalUnit first){
		return new ProgramNode(env);
	}
	
	@Override
	public boolean Parse() throws Exception{
		System.out.println("start");
		LexicalAnalyzer la = env.getInput();
		
		//first
		LexicalUnit lu = la.get();
		//firstを戻す
		env.getInput().unget(lu);
		
		stmt_list = StmtListNode.isMatch(env, lu);
		if (stmt_list == null) return false;

		return stmt_list.Parse();
	}
	
	@Override
	public Value getValue(){
		//System.out.println("Hello");
		return stmt_list.getValue();
	}
}
