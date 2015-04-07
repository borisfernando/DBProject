
public class Expression extends DataType{
	private String condition;
	private boolean negated;
	private boolean error;
	
	public Expression(){}
	public Expression(String name, String condition, boolean negated, boolean error){
		super(name);
		this.condition = condition;
		this.negated = negated;
		this.error = error;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}
	
	public String getCondition(){
		return condition;
	}
	
	public boolean eError(){
		return error;
	}
	
	public boolean isNegated(){
		return negated;
	}
}
