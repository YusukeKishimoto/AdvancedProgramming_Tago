package ReversePolishNotation;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Main {
	/** 逆ポーランド記法用の演算子優先順位 */
	@SuppressWarnings("serial")
	static final Map<Character, Integer> rpnRank = new HashMap<Character, Integer>() {
	    {
	        put('(', 4);    //※数値が高いほど、優先順位が高いとする
	        put('#', 3);    //オペランド(数値)[キーはダミー]
	        put('*', 2);
	        put('/', 2);
	        put('+', 1);
	        put('-', 1);
	        put(')', 0);
	    }
	};
	
	public static void main(String[] args) throws Exception{
		String expr = "(10+20)*(30-40)+50/2";
		System.out.println(toRPN(expr));
	}
	public static final String toRPN(final String expression) {
	    final Deque<Character> stack = new ArrayDeque<Character>();
	    
	    String s = expression.replaceAll("\\s+", "");    //空白文字を取り除く
	    s = "(" + s + ")";                //末尾に")"を付けることで、最後にスタックを吐き出させる
	    final int len = s.length();

	    String ans = "";    //戻値用(RPN式)バッファ
	    String tmp = "";    //数字用バッファ
	    for (int i = 0; i < len; i++) {
	        char c = s.charAt(i);
	        if ('0' <= c && c <= '9') {
	            tmp += c;    //数字１文字ずつのため
	        } else {
	            if (!tmp.equals("")) {
	                if (ans.length() > 0) {
	                    ans += " ";
	                }
	                ans += tmp;
	                tmp = "";
	            }

	            while (!stack.isEmpty() && rpnRank.get(stack.peek()) >= rpnRank.get(c) && stack.peek() != '(') {
	                if (ans.length() > 0) {
	                    ans += " ";
	                }
	                ans += stack.removeFirst();
	            }
	            if (c == ')') {
	                stack.pop();    //'('
	            } else {
	                stack.push(c);
	            }
	        }
	    }

	    return ans;
	}
}
