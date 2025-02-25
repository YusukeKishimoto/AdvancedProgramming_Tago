package newlang4;

import java.util.HashSet;
import java.util.Set;

public class StmtNode extends Node{
	
	Node body;
	
	private StmtNode(Environment env){
		super.env = env;
		super.type = NodeType.STMT; 
	}
	
	private static Set<LexicalType> firstSet = new HashSet<LexicalType>();
	
	static{
		firstSet.add(LexicalType.FOR);
		firstSet.add(LexicalType.NAME);
		firstSet.add(LexicalType.END);
		
	}
	
	public static Node isMatch(Environment env, LexicalUnit first){
		if(!firstSet.contains(first.type)){
			return null;
		}
		return new StmtNode(env);
	}
	
	@Override
	public boolean Parse() throws Exception{
		//luにfirst集合の何かを入れる
		LexicalUnit lu = env.getInput().get();
		//first集合を戻す
		env.getInput().unget(lu);
		
		if(lu.getType() == LexicalType.END){
			body = new Node(NodeType.END);
			//<END>は捨てる
			//env.getInput().get();
			System.out.println("\nEND!");
			return true;
		}
		//FOR文のノード
		body = ForNode.isMatch(env,lu);
		if(body != null){
			//System.out.print("FOR[");
			if(body.Parse() == true){
				//System.out.println("];");
				return true;
			}
			return false;
		}
		
		//関数呼び出しのノード		
		body = CallSubNode.isMatch(env, lu);
		if(body != null){
			if(body.Parse() == true){
				//System.out.println("];");
				return true;
			}
			return false;
		}	
		
		//AssignNodeはsubstのこと
		body = AssignNode.isMatch(env, lu);
		if(body != null){
			if(body.Parse() == true){
				//System.out.println(" ];");
				return true;
			}
			return false ;
		}
		return false;
	}
	
	@Override
	public Value getValue(){
		return body.getValue();
	}
}
