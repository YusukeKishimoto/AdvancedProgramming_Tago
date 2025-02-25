package newlang4;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class StmtListNode extends Node {
	
	Node body;
	
	List<Node> childNodeList = new ArrayList<Node>();
	private static HashSet<LexicalType> firstSet = new HashSet<>();
	static{
		firstSet.add(LexicalType.NAME);
		firstSet.add(LexicalType.FOR);
		firstSet.add(LexicalType.IF);
		firstSet.add(LexicalType.DO);
		firstSet.add(LexicalType.WHILE);
		firstSet.add(LexicalType.END);
	}
	
	private StmtListNode(Environment env){
		super.env = env;
		super.type = NodeType.STMT_LIST;
	}
	
	public static Node isMatch(Environment env, LexicalUnit first){
		if(!firstSet.contains(first.type)){
			return null;
		}
		return new StmtListNode(env);
	}

	@Override
	public boolean Parse() throws Exception{
		
		while(true){
			//first
			LexicalUnit lu = env.getInput().get();
			//System.out.println(lu);
			//NLを読み飛ばす
			if(lu.getType() == LexicalType.NL) continue;
			//EOFだったらおしまい
			//System.out.print(lu);
			if(lu.type==LexicalType.END){
				env.getInput().unget(lu);
				return true;
			}
			if(lu.type==LexicalType.NEXT)return true;
			if(lu.type==LexicalType.ENDIF || lu.type == LexicalType.ELSE ||
			   lu.type == LexicalType.ELSEIF || lu.type == LexicalType.LOOP || lu.type == LexicalType.WEND){
				env.getInput().unget(lu);
				return true;
			}

			//NLじゃなかったので戻す。
			env.getInput().unget(lu);
			//この時点で lu は一番最初から次のLexicalUnitが格納されていることになる？
			//ungetした時点でluに前のノードが入らないとまずい　たとえば<NAME>
			//<NL>を飛ばしてきた場合ungetすると<NL>が入ってしまう？
			
			body = StmtNode.isMatch(env, lu);
			if(body != null){
				if(body.Parse() == false) return false;
				childNodeList.add(body);
				continue;
			}
			
			body = BlockNode.isMatch(env, lu);
			if(body != null){
				if(body.Parse() == false) return false;
				childNodeList.add(body);
				continue;
			}
			
	
			return false;
		}
	}
	@Override
	public Value getValue(){
		for(Node node : childNodeList){
			node.getValue();
		}
		return null;
	}
}
