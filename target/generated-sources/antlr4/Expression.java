
public class Expression extends ValueType{
	
	private boolean eError;
	
	public Expression(String name, String value, boolean eError) {
		super(name,value);
		this.eError = eError;
	}

	@Override
	public String getName() {
		return super.getName();
	} 
	
	public boolean eError(){
		return eError;
	}
}
