import java.util.HashMap;


public class MultipleExpression extends Expression{

	Expression e1;
	Expression e2;
	String opString;
	
	public MultipleExpression(String name, Expression e1, String op, Expression e2, boolean eError) {
		super(name,eError);
		this.e1 = e1;
		opString = op;
		this.e2 = e2;
	}
	
	public Expression getE1(){
		return e1;
	}
	public Expression getE2(){
		return e2;
	}
	public String getOp(){
		return opString;
	}
	
	public String toString(){
		return e1.toString()+" "+opString+" "+e2.toString();
	}
	
	public HashMap<String,String> getUnaries(Expression e, HashMap<String,String> columns){
		if (e.getName().equals("MExpression")){
			MultipleExpression exp = (MultipleExpression) e;
			columns = getUnaries(exp.getE1(),columns);
			columns = getUnaries(exp.getE2(),columns);
		}
		else if (e.getName().equals("Unary")){
			UnaryExpression uExp = (UnaryExpression) e;
			ValueType val = uExp.getVal();
			if (val.getName().equals("ID")){
				IdValueType idVal = (IdValueType) val;
				if (idVal.hasRef()){
					columns.put(idVal.getValue(), idVal.getTableRef());
				}
			}
		}
		return columns;
	}
	
	

}
