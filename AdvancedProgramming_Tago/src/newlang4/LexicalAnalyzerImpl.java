package newlang4;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
	PushbackReader source;
	Map<String,LexicalUnit> reservedMap = new HashMap<String,LexicalUnit>();
	
	public LexicalAnalyzerImpl(InputStream is){
		Reader reader = new InputStreamReader(is);
		source = new PushbackReader(reader);
		
		reservedMap.put("IF", new LexicalUnit(LexicalType.IF));
		reservedMap.put("THEN", new LexicalUnit(LexicalType.THEN));
		reservedMap.put("ELSE", new LexicalUnit(LexicalType.ELSE));
		reservedMap.put("ELSEIF", new LexicalUnit(LexicalType.ELSEIF));
		reservedMap.put("FOR", new LexicalUnit(LexicalType.FOR));
		reservedMap.put("FORALL", new LexicalUnit(LexicalType.FORALL));
		reservedMap.put("NEXT", new LexicalUnit(LexicalType.NEXT));
		reservedMap.put("FUNC", new LexicalUnit(LexicalType.FUNC));
		reservedMap.put("DIM", new LexicalUnit(LexicalType.DIM));
		reservedMap.put("AS", new LexicalUnit(LexicalType.AS));
		reservedMap.put("END", new LexicalUnit(LexicalType.END));
		reservedMap.put("WHILE", new LexicalUnit(LexicalType.WHILE));
		reservedMap.put("DO", new LexicalUnit(LexicalType.DO));	
		reservedMap.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
		reservedMap.put("LOOP", new LexicalUnit(LexicalType.LOOP));
		reservedMap.put("TO", new LexicalUnit(LexicalType.TO));	
		reservedMap.put("WEND", new LexicalUnit(LexicalType.WEND));
		reservedMap.put("EOF", new LexicalUnit(LexicalType.EOF));			
	}
	
	private boolean isAlpha(char c){
		if(c >= 'a' && c <= 'z') return true;
		if(c >= 'A' && c <= 'Z') return true;		
		return false;
	}
	
	private boolean isNumeric(char c){
		if(c >= '0' && c <= '9') return true;
		return false;
	}
			
	private boolean isLiteral(char c){
		if(c == '\"') return true;
		return false;
	}
	
	private boolean isSymbol(char c){
		if(c == '+' || c == '-' || c == '/' || c =='*' || c == '=' || c == '<' || 
		   c == '>' || c == '.' || c == ',' || c == '(' || c == ')' || c == '\n') return true;
		return false;
	}
	
	private  LexicalUnit getAlpha(char c) throws Exception{
		String ret = "";
		
		while(true){
			ret += c;
			int ci = source.read();
			if(ci == -1){
				break;
			}
			c= (char)ci;
			if(!isAlpha(c) && !isNumeric(c)){
				source.unread(ci);
				break;
			}
			
		}
		LexicalUnit ru = reservedMap.get(ret);
		if(ru != null) return ru;
		return new LexicalUnit(LexicalType.NAME, new ValueImpl(ret));
	}		
	
	private LexicalUnit getNumeric(char c) throws Exception{
		String ret = "";
		boolean isdouble = false;
		
		//このままだと 23.23.2 など小数点を２回書いてしまうミスを判別できない.
		while(true){
			ret += c;
			int ci = source.read();
			if(ci == -1){
				break;
			}
			c = (char)ci;
			if(c == '.') isdouble = true;
			if(!isNumeric(c) && c != '.'){
				source.unread(ci);
				break;
			}	
		}

		if(isdouble) return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(ret));
		return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(ret));
	}
	
	private LexicalUnit getLiteral() throws Exception{
		char c;
		String ret = "";
		while(true){
			int ci = source.read();
			if(ci == -1){
				break;
			}
			c= (char)ci;
			if(c == '\"'){
				ci = source.read();
				break;
			}
			ret += c;
		}
		return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(ret));
		
	}
	
	private LexicalUnit getSymbol(char c) throws Exception{
		int ci;
		if(c=='+') return new LexicalUnit(LexicalType.ADD);
		if(c=='-') return new LexicalUnit(LexicalType.SUB);
		if(c=='/') return new LexicalUnit(LexicalType.DIV);
		if(c=='*') return new LexicalUnit(LexicalType.MUL);
		if(c=='.') return new LexicalUnit(LexicalType.DOT);
		if(c==',') return new LexicalUnit(LexicalType.COMMA);
		if(c=='(') return new LexicalUnit(LexicalType.RP);
		if(c==')') return new LexicalUnit(LexicalType.LP);
		
		//=,<,>,<=,>=,=<,=>,<>の処理
		if(c=='<'){
			ci = source.read();
			c = (char)ci;
			if(c == '=') return new LexicalUnit(LexicalType.LE);
			if(c == '>') return new LexicalUnit(LexicalType.NE);
			source.unread(ci);
			return new LexicalUnit(LexicalType.LT);
		}
		if(c=='>'){
			ci = source.read();
			c = (char)ci;
			if(c == '=') return new LexicalUnit(LexicalType.GE);
			source.unread(ci);
			return new LexicalUnit(LexicalType.GT);
		}
		if(c=='='){
			ci = source.read();
			c = (char)ci;
			if(c == '<') return new LexicalUnit(LexicalType.LE);
			if(c == '>') return new LexicalUnit(LexicalType.GE);
			source.unread(ci);
			return new LexicalUnit(LexicalType.EQ);
		}
		
		//残るは改行のみ！
		return new LexicalUnit(LexicalType.NL);
	}
	
	@Override
	public LexicalUnit get() throws Exception {
		int ci = source.read();
		if(ci == -1){
			return new LexicalUnit(LexicalType.EOF);
		}		
		char c =(char) ci;		
		//スペースかタブの場合は読み飛ばす
		while(c == ' ' || c == '\t'){
			ci = source.read();
			c = (char) ci;
		}
		if(isAlpha(c))
			return getAlpha(c);
		if(isNumeric(c))
			return getNumeric(c);
		if(isLiteral(c))
			return getLiteral();
		if(isSymbol(c))
			return getSymbol(c);
		return null;
	}
	
	@Override
	public boolean expect(LexicalType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unget(LexicalUnit token) {
		// TODO Auto-generated method stub
		
	}
	
	
}
