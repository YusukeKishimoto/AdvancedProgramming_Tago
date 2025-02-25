package newlang4;

public class SquareRootFunction extends Function{
	
	@Override
	public Value invoke(ExprListNode exprlist){
		double sqrtval;
		if(exprlist.exprNodeList.size() != 1){
			System.out.println("Argument is one");
			return null;
		}
		
		if(exprlist.exprNodeList.get(0).getType() == NodeType.VARIABLE){
			sqrtval = exprlist.env.var_table.get(exprlist.exprNodeList.get(0).val.getSValue()).val.getDValue();
			sqrtval = Math.sqrt(sqrtval);
		}else{		
			sqrtval = Math.sqrt(exprlist.exprNodeList.get(0).val.getDValue());		
		}
		return new ValueImpl(sqrtval);
	}

}
