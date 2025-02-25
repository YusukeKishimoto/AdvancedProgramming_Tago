package newlang4;

public class ModuloFunction extends Function{
	@Override
	public Value invoke(ExprListNode exprlist){
		double[] mod = new double[2];
		
		if(exprlist.exprNodeList.size() != 2){
			System.out.println("Argument is two");
			return null;
		}

		for(int i=0; i<2; i++){			
			if(exprlist.exprNodeList.get(i).getType() == NodeType.VARIABLE){
				mod[i] = exprlist.env.var_table.get(exprlist.exprNodeList.get(i).getValue().getSValue()).getValue().getDValue();
			}else{
				mod[i] = exprlist.exprNodeList.get(i).getValue().getDValue();
			}
			
		}

		return new ValueImpl(mod[0]%mod[1]);
	}
}
