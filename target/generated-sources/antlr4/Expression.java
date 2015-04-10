
public class Expression extends DataType{
	
	boolean eError;
	
	public Expression(String name, boolean eError) {
		super(name);
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
