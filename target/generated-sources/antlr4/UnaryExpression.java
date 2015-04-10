
public class UnaryExpression extends Expression {
	
	ValueType val;
	
	public UnaryExpression(String name, ValueType val, boolean eError) {
		super(name,eError);
		this.val = val;
	}
	
	public String getValue(){
		return val.getValue();
	}
	
	public ValueType getVal(){
		return val;
	}
	public String toString(){
		return val.toString();
	}
	
}
