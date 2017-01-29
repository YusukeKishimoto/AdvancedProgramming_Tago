package newlang4;

public class ValueImpl implements Value{

	ValueType type;
	String svalue;
	int ivalue;
	double dvalue;
	boolean bvalue;
	
	public ValueImpl(ValueType type) {
		this.type = type;
	}
	
	public ValueImpl(int ivalue){
		this.ivalue = ivalue;
		this.dvalue = (double)ivalue;
		this.type = ValueType.INTEGER;
	}

	public ValueImpl(String svalue) {
		this.svalue = svalue;
		this.type = ValueType.STRING;
	}
	public ValueImpl(double dvalue){
		this.dvalue = dvalue;
		if(dvalue == (int)dvalue){
			this.type = ValueType.INTEGER;
			this.ivalue = (int)dvalue;
		}else{
			this.type = ValueType.DOUBLE;			
		}
	}
	public ValueImpl(boolean bvalue){
		this.bvalue = bvalue;
	}
	
	@Override
	public String getSValue() {
		// TODO Auto-generated method stub
		
		return svalue;
	}

	@Override
	public int getIValue() {
		// TODO Auto-generated method stub
		return ivalue;
	}

	@Override
	public double getDValue() {
		// TODO Auto-generated method stub
		return dvalue;
	}

	@Override
	public boolean getBValue() {
		// TODO Auto-generated method stub
		return bvalue;
	}

	@Override
	public ValueType getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
