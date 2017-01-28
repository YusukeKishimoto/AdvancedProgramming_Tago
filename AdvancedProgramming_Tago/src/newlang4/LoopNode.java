package newlang4;

public class LoopNode extends Node{
	Node stmt_list;
	Node cond;
	LexicalUnit loopunit;
	//true -> 前値判定 false -> 後値判定
	boolean conddecision;
	
	private LoopNode(Environment env){
		super.env = env;
	}
	
	public static Node isMatch(Environment env, LexicalUnit first){		
		if(first.getType() == LexicalType.WHILE || first.getType() == LexicalType.DO) return new LoopNode(env);		
		return null;
	}
	
	@Override
	public boolean Parse() throws Exception{
		LexicalUnit lu = env.getInput().get();
		if(lu.getType() == LexicalType.DO){			
			System.out.println("DO : ");
			//DOの次
			lu = env.getInput().get();
			//NLの場合
			if(lu.getType() == LexicalType.NL){
				conddecision = false;
				
				lu = env.getInput().get();
				env.getInput().unget(lu);
				stmt_list = StmtListNode.isMatch(env, lu);
				
				if(stmt_list == null) return false;
				if(stmt_list.Parse() == false) return false;
				lu = env.getInput().get();
				
				if(lu.getType() != LexicalType.LOOP) return false;
				lu = env.getInput().get();
				
				if(lu.getType() != LexicalType.UNTIL && lu.getType() != LexicalType.WHILE) return false;
				loopunit = lu;
				
				System.out.println(loopunit);
				
				lu = env.getInput().get();
				env.getInput().unget(lu);
				cond = CondNode.getCondition(env, lu);
				if(cond != null) return true;				
				return false;
			}
			//UNTIL,WHILEの場合
			if(lu.getType() == LexicalType.UNTIL || lu.getType() == LexicalType.WHILE){
				conddecision = true;
				loopunit = lu;
				System.out.println(loopunit);
				lu = env.getInput().get();
				env.getInput().unget(lu);
				
				cond = CondNode.getCondition(env, lu);				
				if(cond == null) return false;
				
				if(env.getInput().get().type != LexicalType.NL)return false;  //NL
				lu = env.getInput().get();
				env.getInput().unget(lu);
				
				stmt_list = StmtListNode.isMatch(env, lu);
				if(stmt_list == null) return false;
				if(stmt_list.Parse() == false) return false;

				lu = env.getInput().get();
				if(lu.type == LexicalType.LOOP){
					System.out.println("LOOP");
					return true;
				}
				return false;
			}
		}
		if(lu.getType() == LexicalType.WHILE){
			System.out.println("WHILE: ");
			//while文はDO..UNTILと
			loopunit = new LexicalUnit(LexicalType.WHILE);
			conddecision = true;
			
			lu = env.getInput().get();
			env.getInput().unget(lu);
			
			cond = CondNode.getCondition(env, lu);
			if(cond == null) return false;
			
			if(env.getInput().get().type != LexicalType.NL)return false;  //NL
			lu = env.getInput().get();
			env.getInput().unget(lu);
			
			stmt_list = StmtListNode.isMatch(env, lu);
			if(stmt_list == null) return false;
			if(stmt_list.Parse() == false) return false;
			
			if(env.getInput().get().getType() == LexicalType.WEND) return true;
		}
		return false;
	}
	
	@Override
	public Value getValue(){	
		
		if(conddecision == false ){
			stmt_list.getValue();
		}
		while(true){
			if(loopunit.type == LexicalType.WHILE){
				if(cond.getValue() != null){
					stmt_list.getValue();
					continue;
				}
				break;
			}
			if(loopunit.type == LexicalType.UNTIL){
				if(cond.getValue() == null){
					stmt_list.getValue();
					continue;
				}
				break;
			}
		}
		return null;
	}
}
