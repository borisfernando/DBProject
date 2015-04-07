
public class DataType implements Type{

	protected String name;	
	
	public DataType(String name){
		this.name = name;		
	}
	
	@Override
	public String getName() {
		return name;
	}
}
