package newlang3;


public interface LexicalAnalyzer {

	/*
	 * getと言われる度に字句を返す
	 * 改行も字句として見る
	 * 英字が来たら一つの字句の始まり
	 * 2文字目以降が英数字が続く限り、字句と認識する
	 * 字句には予約語(DO,UNTIL,LOOP,END,=,<,改行...etc)と名前(a,aA,a1,azb...etc)と数字が存在する
	 * 数字が来たら続く限り、数字の字句として扱う
	 * 数字の次に "." が来た場合も数字とみなす
	 * またその他にリテラル(ダブルクオーテーションで囲まれる字句)が存在する
	*/
	public LexicalUnit get() throws Exception;
    public boolean expect(LexicalType type);
    public void unget(LexicalUnit token);    
}
