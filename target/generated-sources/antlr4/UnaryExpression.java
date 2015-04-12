
public class UnaryExpression extends Expression {
	
	String type;
	
	public UnaryExpression(String name, String val, String type, boolean eError) {
		super(name,type,eError);
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	public String toString(){
		return super.toString();
	}
	
}
