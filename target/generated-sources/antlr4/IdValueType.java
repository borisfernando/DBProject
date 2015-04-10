
public class IdValueType extends ValueType{
	
	private String tableRef;
	private boolean hasRef;
	
	public IdValueType(String name, String column, String tableRef, boolean hasRef) {
		super(name, column);
		this.tableRef = tableRef;
		this.hasRef = hasRef;
	}
	
	public String getTableRef(){
		return tableRef;
	}
	public boolean hasRef(){
		return hasRef;
	}
	
	public String toString(){
		return super.toString();
	}
	
}
