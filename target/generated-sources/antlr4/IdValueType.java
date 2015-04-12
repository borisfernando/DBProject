
public class IdValueType extends UnaryExpression{
	
	private String tableRef;
	private boolean hasRef;
	
	public IdValueType(String name, String val, String type, String tableRef, boolean error, boolean hasRef) {
		super(name, val, type, error);
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
