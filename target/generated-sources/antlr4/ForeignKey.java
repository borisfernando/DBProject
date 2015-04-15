
public class ForeignKey extends Key {
	String[] references;
	String tableReference;
	boolean error;
	
	public ForeignKey(String name, String id, String[] columnsId, String tableReference, String[] references, boolean error){
		super(name,id,columnsId);
		this.references = references;
		this.tableReference = tableReference;
		this.error = error;
	}
	
	public String getReference(int i){
		return references[i];
	}
	
	public int getLengthReferences(){
		return references.length;
	}
	
	public String getTableReference(){
		return tableReference;
	}
	
	public boolean eError(){
		return error;
	}
}
