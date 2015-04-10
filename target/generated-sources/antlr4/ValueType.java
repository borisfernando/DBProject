
public class ValueType extends DataType {
	String value;
	
	public ValueType(String name,String value){
		super(name);
		this.value = value;
	}
	public String getValue(){
		return value;
	}
	public String toString(){
		return value;
	}
	
}

