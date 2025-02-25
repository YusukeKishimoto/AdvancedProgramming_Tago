package newlang4;

public class Node {
    NodeType type;
    Environment env;
    
    //BinaryNodeで一つの数字に対してNodeを持っているから値を覚えておかないと？
    Value val;
    
    public Node next;

    /** Creates a new instance of Node */
    public Node() {
    }
    public Node(NodeType my_type) {
        type = my_type;
    }
    public Node(Environment my_env) {
        env = my_env;
    }
    //追記
    public Node(NodeType my_type, Value my_val){
    	type = my_type;
    	val = my_val;
    }

    public NodeType getType() {
        return type;
    }
    
    public boolean Parse() throws Exception{
        return true;
    }
    
    public Value getValue() {
        return val;
    }
    
    public String toString() {
    	if (type == NodeType.END) return "END";
    	else return "Node";        
    }

}
