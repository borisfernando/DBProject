
public class DataType implements Type{

	protected String name;	
	
	public DataType(){}
	public DataType(String name){
		this.name = name;		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
}
